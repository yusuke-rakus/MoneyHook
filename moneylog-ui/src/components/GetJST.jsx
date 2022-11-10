export const getJST = (date) => {
  let jst = date;
  jst.setHours(jst.getHours() + 9);
  return jst;
};
