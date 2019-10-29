import moment from 'moment';

let MISSING_FIELDS = 'Missing fields!';

function noEmptyParams(...fields) {
  return fields.reduce((acc, field) => (field !== undefined), true);
}

/*
 solely returns the field without any transformation.
 this should be for string
*/
function noTransformation(row, field) {
  if (!noEmptyParams(field)) {
    throw MISSING_FIELDS;
  }

  return row[field];
}

export function leaveEmpty() {
  return '';
}

/*
  takes a dateField (YYYY-M-D) and a timeField (H:m), and returns a dateTime field in the correct format.
 */
function transformDateAndTimeToDateTime(row, dateField, timeField) {
  if (!noEmptyParams(dateField, timeField)) {
    throw MISSING_FIELDS;
  }

  const date = row[dateField];
  const time = row[timeField];
  const combined = moment(`${date} ${time}`, 'YYYY-M-D H:m', true);

  if (!combined.isValid()) {
    throw 'Problem with parsing date and time!';
  }

  return combined.format('YYYY-MM-DD hh:mm:ss');
}

/*
  takes a dateTime field (YYYY-M-D H:m), returns a dateTime field in the correct format.
 */
function transformToDateTime(row, field) {
  if (!noEmptyParams(field)) {
    throw MISSING_FIELDS;
  }

  const parsed = moment(row[field], 'YYYY-M-D H:m', true);

  if (!parsed.isValid()) {
    throw 'Problem with parsing date time!';
  }

  return parsed.format('YYYY-MM-DD hh:mm:ss');
}

/*
  parse to an int
 */
function transformToInt(row, field) {
  if (!noEmptyParams(field)) {
    throw MISSING_FIELDS;
  }

  const parsed = parseInt(row[field]);

  if (isNaN(parsed)) {
    throw 'Problem with parsing int!';
  }

  return parsed;
}

/*
  parse to javascript number
 */
function transformToFloat(row, field) {
  if (!noEmptyParams(field)) {
    throw MISSING_FIELDS;
  }

  const parsed = parseFloat(row[field]);

  if (isNaN(parsed)) {
    throw 'Problem with parsing double/float!';
  }

  return parsed;
}

/*
  parse confidence.
  this is copied from chairvise2.0. hardcoded, should take a look at this again
 */
function transformToConfidenceLevel(row, field) {
  let rawStringList = row[field].toLocaleLowerCase().split('\n');
  for (let i = 0; i < rawStringList.length; i++) {
    let rawString = rawStringList[i];
    if (rawString.includes('confidence:')) {
      // hard code the processing
      let confidenceValueString = rawString.trim().split(':')[1];
      return parseFloat(confidenceValueString);
    }
  }
  return 0;
}

/*
  parse list of authors.
  this is copied from chairvise2.0. this is hardcoded so should take a look how to improve this.
 */
function transformToAuthorList(row, field) {
  let dataList = row[field].split('and');
  if (dataList.length === 1) {
    return dataList.map(author => author.trim());
  } else {
    let lastAuthor = dataList[1];
    let allAuthors = dataList[0].split(',');
    allAuthors.push(lastAuthor);
    return allAuthors.map(author => author.trim());
  }
}

// options
export default {
  String: [
    {
      value: noTransformation,
      name: 'none'
    },
    {
      value: leaveEmpty,
      name: 'leave empty'
    }
  ],
  int: [
    {
      value: transformToInt,
      name: 'to int'
    }
  ],
  double: [
    {
      value: transformToFloat,
      name: 'to double/float'
    },
    {
      value: transformToConfidenceLevel,
      name: 'to confidence level'
    }
  ],
  Date: [
    {
      value: transformToDateTime,
      name: 'do not change'
    },
    {
      value: transformDateAndTimeToDateTime,
      name: 'date + time to datetime'
    }
  ],
  List: [
    {
      value: transformToAuthorList,
      name: 'author list'
    }
  ]
}
