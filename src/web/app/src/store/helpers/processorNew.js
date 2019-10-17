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

