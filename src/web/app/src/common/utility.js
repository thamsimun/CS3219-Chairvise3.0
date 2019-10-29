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
  takes in a list of lists and places the item into one of the lists.
  each list should ideally have 1 item.
  tries to place the item up in the list, any excess will be in the last list.
  this mutates xss.
 */
export const distribute = (xss, item) => {
    for (let n = 0; n < xss.length; n++) {
      if (xss[n].length === 0) { // if the list is empty
        xss[n].push(item);
        return;
      }
      if (n === xss.length - 1) { // if the list is the last
        xss[n].push(item);
        return;
      }
    }
};

/*
  takes in a list of lists (call is xss), the column index and the list index.
  removes xss[colIdx][lstIdx].
  this method uses map and filter and hence it does not mutate the original xss.
 */
export const removeItem = (xss, colIdx, lstIdx) => {
  return xss.map((xs, idx) => idx !== colIdx ? xs : xs.filter((x, idx) => idx !== lstIdx));
};
