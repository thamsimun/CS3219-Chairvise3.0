export const deepCopy = (obj) => {
  return JSON.parse(JSON.stringify(obj));
};

export const filterPredefinedMap = (mappedIdArray, originalArray) => {
  let result = [];
  for (let i = 0; i < mappedIdArray.length; i++) {
    if (mappedIdArray[i] < originalArray.length) {
      result.push(mappedIdArray[i]);
    }
  }
  return result;
};

/*
  takes in a list and splits it into a list of lists (length n).
  the last list contains the remaining elements if xs.length > n
 */
export const distribute = (xs, n) => {
  return [...Array(n)].map((field, index) =>
  (index === xs.length - 1)
    ? xs.slice(index)
    : xs.slice(index, index + 1));
};

