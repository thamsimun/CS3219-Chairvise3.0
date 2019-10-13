import moment from 'moment';

function processDouble(raw) {
  if (!isNaN(parseFloat(raw))) {
    return parseFloat(raw);
  }
  // if not even string, return default value 0
  if (typeof (raw) !== "string") {
    return 0;
  }

  // TODO: figure out a better way to parse confidence level
  // below is a hack
  let rawStringList = raw.toLocaleLowerCase().split("\n");
  for (let i = 0; i < rawStringList.length; i++) {
    let rawString = rawStringList[i];
    if (rawString.includes("confidence:")) {
      // hard code the processing
      let confidenceValueString = rawString.trim().split(":")[1];
      return parseFloat(confidenceValueString);
    }
  }
  return 0;
}

function processAuthors(raw) {
  let dataList = raw.split('and');
  if (dataList.length === 1) {
    return dataList.map(author => author.trim());
  } else {
    let lastAuthor = dataList[1];
    let allAuthors = dataList[0].split(',');
    allAuthors.push(lastAuthor);
    return allAuthors.map(author => author.trim());
  }
}

/*
  solely returns the field without any transformation.
 */
export function noTransformation(row, field) {
  return row[field];
}

/*
  takes a dateField and a timeField, and returns a dateTime field.
 */
function transformToDateTime(row, dateField, timeField) {
  let date = row[dateField];
  let time = row[timeField];

  // check for errors; is it an invalid date?
  return moment(`${date} ${time}`, 'YYYY-M-D H:m').format('YYYY-MM-DD hh:mm:ss');
}

/*
  takes in a data object (still with the key-value pairings), a mapping list and a list of transformations
  return a list of list of rows (only values and not the keys)
 */
export function applyTransformations(data, mappingList, transformations) {
  let transformedData = [];

  data.forEach(row => {
    let transformedRow = [];
    mappingList.forEach((list, index) => {
      transformedRow.push(transformations[index](row, ...list));
    });
    transformedData.push(transformedRow);
  });

  return transformedData;
}

/*
  data should be clear of unwanted fields.
  data fields should be ordered as in dbFields.
  dbSchema is the chosen dbSchema.
 */
export function processMapping(data, fieldMetaData) {
  let result = [];
  let dbFields = fieldMetaData;

  for (let i = 0; i < data.length; i++) {
    let row = data[i];
    let resultingData = {};

    row.forEach((cell, idx) => {
      let cellData;
      let fieldType = dbFields[idx].type;

      switch (fieldType) {
        // TODO: missing LocalData and LocalTime cases
        case 'Date':
          cellData = moment(cell, 'YYYY-M-D H:m').format('YYYY-MM-DD hh:mm:ss');
          break;
        case 'int':
          cellData = parseInt(cell);
          break;
        case 'double':
          cellData = processDouble(cell);
          break;
        case 'List':
          cellData = processAuthors(cell);
          break;
        default:
          cellData = cell;
      }

      resultingData[dbFields[idx].jsonProperty] = cellData;
    });

    result.push(resultingData);
  }
  return result;
}