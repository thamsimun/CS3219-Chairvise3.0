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
  takes in a data object (still with the key-value pairings), a mapping list, a list of transformations
  and the field meta data
  return the mapped data (list of objects that fit the db)
 */
export function applyTransformations(data, mappingList, transformations, fieldMetaData) {
  let transformedData = [];

  data.forEach(row => {
    let resultingData = {};
    mappingList.forEach((list, index) => {
      resultingData[fieldMetaData[index].jsonProperty] = transformations[index](row, ...list);
    });
    transformedData.push(resultingData);
  });

  return transformedData;
}

