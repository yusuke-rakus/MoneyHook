export const getJST = (date) => {
  let jst = date;
  jst.setHours(jst.getHours() + 9);
  return jst;
};

export const getCookieRange = (date) => {
  let jst = date;
  jst.setMonth(jst.getMonth() + 3);
  return jst;
};
