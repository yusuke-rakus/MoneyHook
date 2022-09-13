import { Button, IconButton } from "@mui/material";
import React, { useState } from "react";
import SettingsFixed from "./SettingsFixed";
import AddCircleIcon from "@mui/icons-material/AddCircle";

const SettingsFixedList = () => {
  /** 固定費の編集 */
  const [monthlyTransactionList, setMonthlyTransactionList] = useState([
    {
      monthlyTransactionId: 1,
      monthlyTransactionName: "家賃",
      monthlyTransactionAmount: -70000,
      monthlyTransactionDate: 25,
      categoryId: "1",
      categoryName: "住宅",
      subCategoryId: "1",
      subCategoryName: "家賃",
    },
    {
      monthlyTransactionId: 2,
      monthlyTransactionName: "電気代",
      monthlyTransactionAmount: -7000,
      monthlyTransactionDate: 30,
      categoryId: "2",
      categoryName: "水道光熱費",
      subCategoryId: "2",
      subCategoryName: "なし",
    },
    {
      monthlyTransactionId: 3,
      monthlyTransactionName: "給与",
      monthlyTransactionAmount: 200000,
      monthlyTransactionDate: 25,
      categoryId: "3",
      categoryName: "収入",
      subCategoryId: "3",
      subCategoryName: "給与",
    },
  ]);

  /** 固定費データ入力欄の追加 */
  const AddFixedDataInput = () => {
    let mtId = monthlyTransactionList.slice(-1)[0].monthlyTransactionId + 1;
    setMonthlyTransactionList([
      ...monthlyTransactionList,
      {
        monthlyTransactionId: mtId,
        monthlyTransactionName: null,
        monthlyTransactionAmount: null,
        monthlyTransactionDate: null,
        categoryName: null,
        subCategoryName: null,
      },
    ]);
  };

  /** 登録処理 */
  const register = () => {
    monthlyTransactionList.map((data) => {
      console.log(data);
    });
  };

  /** キャンセル */
  const cancel = () => {
    console.log(monthlyTransactionList);
  };

  return (
    <div className="containerBox">
      <p className="settingsTitle">固定費の編集</p>
      <hr className="border" />

      {/* 月次費用 */}
      <div className="fixedListArea">
        {monthlyTransactionList.map((data, i) => {
          return (
            <SettingsFixed
              key={i}
              data={data}
              monthlyTransactionList={monthlyTransactionList}
              setMonthlyTransactionList={setMonthlyTransactionList}
            />
          );
        })}
      </div>

      <div className="addArea" onClick={AddFixedDataInput}>
        <IconButton>
          <AddCircleIcon />
        </IconButton>
      </div>

      <div className="fixedSettingsButtons">
        <Button onClick={cancel} variant="contained" color="inherit">
          キャンセル
        </Button>
        <Button onClick={register} variant="contained">
          登録
        </Button>
      </div>
    </div>
  );
};

export default SettingsFixedList;
