import moment from 'moment';

/*
 solely returns the field without any transformation.
 this should be for string
*/
export function noTransformation(row, field) {
  return row[field];
}

export function leaveEmpty(row) {
  return "";
}

/*
  takes a dateField and a timeField, and returns a dateTime field.
 */
export function transformToDateTime(row, dateField, timeField) {
  let date = row[dateField];
  let time = row[timeField];

  // check for errors; is it an invalid date?
  return moment(`${date} ${time}`, 'YYYY-M-D H:m').format('YYYY-MM-DD hh:mm:ss');
}

/*
  parse to an int
 */
export function transformToInt(row, field) {
  return parseInt(row[field]);
}

/*
  parse to javascript number
 */
export function transformToFloat(row, field) {
  return parseFloat(row[field]);
}

/*
  parse confidence.
  this is copied from chairvise2.0. hardcoded, should take a look at this again
 */
export function transformToConfidenceLevel(row, field) {
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
export function transformToAuthorList(row, field) {
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
      value: (row, field) => moment(cell, 'YYYY-M-D H:m').format('YYYY-MM-DD hh:mm:ss'),
      name: 'do not change'
    },
    {
      value: transformToDateTime,
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
