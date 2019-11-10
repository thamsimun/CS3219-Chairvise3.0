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
  parse accept/reject status
 */
function acceptReject(row, field) {
  if (!noEmptyParams(field)) {
    throw MISSING_FIELDS;
  }
  if (row[field].toLowerCase().includes("reject")) { // decision is in y[6], but change to this either reject/
    // accept fist
    return "reject";
  } else {
    return "accept";
  }
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
  const combined = moment(`${date} ${time}`);

  if (!combined.isValid()) {
    throw 'Problem with parsing date and time!';
  }

  return combined.format('YYYY-MM-DD hh:mm:ss');
}

// /*
//  Takes a GMT dateTime field (SoftConf Review) and returns dateTime field in correct format.
//  */
// function transformGMTtoDateTime(row, gmtField) {
//   if (!noEmptyParams(gmtField)) {
//     throw MISSING_FIELDS;
//   }
//   const gmt = row[gmtField];
//   const output = moment(gmt,'YYYY-MM-DD HH:mm:ss zz', true);
//   if (!output.isValid()) {
//     throw 'Issue with converting GMT to desired time format'
//   }
//   return output.format('YYYY-MM-DD hh:mm:ss');
// }
//
// /*
//  Takes a full GMT dateTime field (SoftConf Submission) and returns dateTime field in correct format.
//  */
// function transformFullGMTtoDateTime(row, gmtField) {
//   if (!noEmptyParams(gmtField)) {
//     throw MISSING_FIELDS;
//   }
//   const gmt = row[gmtField];
//   const output = moment(gmt, 'D MMM YYYY HH:mm:ss zz', true);
//   if (!output.isValid()) {
//     throw 'Issue with converting full GMT to desired time format'
//   }
//   return output.format('YYYY-MM-DD hh:mm:ss');
// }

function properTime(row, field) {
  if (!noEmptyParams(field)) {
    throw MISSING_FIELDS;
  }
  let time = row[field];
  let output = moment(time);
  if (!output.isValid()) {
    throw 'Issue recognising time field using MomentJs'
  }
  return output.format('YYYY-MM-DD hh:mm:ss');
}

// /*
//   takes a dateTime field (YYYY-M-D H:m), returns a dateTime field in the correct format.
//  */
// function transformToDateTime(row, field) {
//   if (!noEmptyParams(field)) {
//     throw MISSING_FIELDS;
//   }
//   const parsed = moment(row[field], 'YYYY-M-D H:m', true);
//   if (!parsed.isValid()) {
//     throw 'Problem with parsing date time!';
//   }
//   return parsed.format('YYYY-MM-DD hh:mm:ss');
// }

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
  parse list of authors.
  this is copied from chairvise2.0. this is hardcoded so should take a look how to improve this.
 */
function transformToAuthorList(row, field) {
  // Split according to 'and'
  let dataList = row[field].split('and');
  if (dataList.length === 1) {
    dataList = row[field].split(' xxx ');
    if (dataList.length === 1) {
      return dataList.map(author => author.trim());
    } else {
      let lastAuthor = dataList[1];
      let allAuthors = dataList[0].split(',');
      // Because for SoftConf there is no 'and', only ' xxx ', so we have to split again if necessary
      allAuthors.push(lastAuthor);
      return allAuthors.map(author => author.trim());
    }
  }
  let lastAuthor = dataList[1];
  let allAuthors = dataList[0].split(',');
    allAuthors.push(lastAuthor);
  return allAuthors.map(author => author.trim());
}

function obtainRecommend(row, field) {
  if (!noEmptyParams(field)) {
    throw MISSING_FIELDS;
  }
  let res = "undef";
  let fields = row[field].split('\n');      // split by newline
  for (let i = 0; i<fields.length; i++) {   // iterate through split elements
    if (fields[i].toLowerCase().includes('recommend')) { // if element contains 'eval'
      if (fields[i].toLowerCase().includes('yes')) {
        res = 'yes';
      } else res = 'no';
    }
  }
  return res;
}

/*
  parse confidence.
  this is copied from chairvise2.0. hardcoded, should take a look at this again
 */
function transformToEvalScore(row, field) {
  let rawStringList = row[field].toLocaleLowerCase().split('\n');
  for (let i = 0; i < rawStringList.length; i++) {
    let rawString = rawStringList[i];
    if (rawString.includes('evaluation')) {
      // hard code the processing
      let confidenceValueString = rawString.trim().split(':')[1];
      return parseFloat(confidenceValueString);
    }
  }
  return 0;
}

/*
  parse confidence.
  this is copied from chairvise2.0. hardcoded, should take a look at this again
 */
function transformToConfidenceLevel(row, field) {
  let rawStringList = row[field].toLocaleLowerCase().split('\n');
  for (let i = 0; i < rawStringList.length; i++) {
    let rawString = rawStringList[i];
    if (rawString.includes('confidence')) {
      // hard code the processing
      let confidenceValueString = rawString.trim().split(':')[1];
      return parseFloat(confidenceValueString);
    }
  }
  return 0;
}

//
// function obtainEvalScore(row, field) {
//   if (!noEmptyParams(field)) {
//     throw MISSING_FIELDS;
//   }
//   let res;
//   let fields = row[field].split('\n');      // split by newline
//   for (let i = 0; i<fields.length; i++) {   // iterate through split elements
//     if (fields[i].toLowerCase().includes('eval')) { // if element contains 'eval'
//       res = field[i].replace(/[^\d.-]/g, ''); // remove all non-integers except '-' for negative values
//     }
//   }
//   return parseFloat(res)   // "Overall evaluation: -3" should return "-3"=
// }

// function obtainConfidence(row, field) {
//   if (!noEmptyParams(field)) {
//     throw MISSING_FIELDS;
//   }
//   let res = null;
//   let fields = row[field].split('\n');      // split by newline
//   for (let i = 0; i<fields.length; i++) {   // iterate through split elements
//     if (fields[i].toLowerCase().includes('conf')) { // if element contains 'confidence'
//       res = field[i].replace(/[^\d.-]/g, ''); // remove all non-integers except '-' for negative values
//     }
//   }
//   return parseFloat(res);   // "confidence level: -3" should return "-3"
// }

// options
export default {
  String: [
    {
      value: leaveEmpty,
      name: '-no data-'
    },
    {
      value: noTransformation,
      name: '-no change-'
    },
    {
      value: obtainRecommend,
      name: 'Get Recommendation'
    },
    {
      value: acceptReject,
      name: 'Get Accept/Reject status'
    },

  ],
  int: [
    {
      value: leaveEmpty,
      name: '-no data-'
    },
    {
      value: transformToInt,
      name: '-no change-'
    },

  ],
  double: [
    {
      value: leaveEmpty,
      name: '-no data-'
    },
    {
      value: transformToFloat,
      name: '-no change-'
    },
    {
      value: transformToConfidenceLevel,
      name: 'Get Confidence Level'
    },
    {
      value: transformToEvalScore,
      name: 'Get Evaluation Score'
    },
  ],
  Date: [
    {
      value: leaveEmpty,
      name: '-no data-'
    },
    {
      value: transformDateAndTimeToDateTime,
      name: 'Date + Time'
    },
    // {
    //   value: transformToDateTime,
    //   name: 'YYYY-M-D H:m'
    // },
    // {
    //   value: transformGMTtoDateTime,
    //   name: 'YYYY-MM-DD HH:mm:ss'
    // },
    // {
    //   value: transformFullGMTtoDateTime,
    //   name: 'D MMM YYYY HH:mm:ss'
    // },
    {
      value: properTime,
      name: 'Proper Format'
    }

  ],
  List: [
    {
      value: leaveEmpty,
      name: '-no data-'
    },
    {
      value: transformToAuthorList,
      name: 'Store as Author list'
    }
  ]
}
