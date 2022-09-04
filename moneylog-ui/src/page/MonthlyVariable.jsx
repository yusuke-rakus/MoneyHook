import React from "react";
/** CSS */
import "./page_CSS/MonthlyVariable.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import VariableCategoryGroup from "../components/VariableCategoryGroup";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";

const MonthlyVariable = () => {
  /** 今月 */
  let date = new Date();
  let formatday = `${date.getFullYear()}-${date.getMonth() + 1}-1`;

  const monthlyTotalVariable = 38000;

  const variableCategoryData = [
    {
      categoryName: "趣味",
      categoryTotalAmount: 20000,
      subCategoryList: [
        {
          subCategoryName: "園芸",
          subCategoryTotalAmount: 10000,
          transactionList: [
            { transactionName: "盆栽", transactionAmount: 4000 },
          ],
        },
        {
          subCategoryName: "スポーツ",
          subCategoryTotalAmount: 10000,
          transactionList: [
            { transactionName: "ゲートボール", transactionAmount: 8000 },
            { transactionName: "少林寺拳法", transactionAmount: 2000 },
          ],
        },
      ],
    },
    {
      categoryName: "ショッピング",
      categoryTotalAmount: 18000,
      subCategoryList: [
        {
          subCategoryName: "調理器具",
          subCategoryTotalAmount: 8000,
          transactionList: [
            { transactionName: "ボウル", transactionAmount: 2000 },
            { transactionName: "フライパン", transactionAmount: 8000 },
          ],
        },
        {
          subCategoryName: "自転車用品",
          subCategoryTotalAmount: 7000,
          transactionList: [
            { transactionName: "タイヤチューブ", transactionAmount: 3000 },
            { transactionName: "サドル", transactionAmount: 4000 },
          ],
        },
      ],
    },
  ];

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        <span>{date.getMonth() + 1}月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
      </div>

      {/* 変動費合計 */}
      <div className="monthlyVariableTitleArea">
        <span>変動費合計</span>
        <span>{monthlyTotalVariable.toLocaleString()}</span>
      </div>

      {/* 変動費データ */}
      <div className="variableDataArea">
        <VariableCategoryGroup variableCategoryData={variableCategoryData} />
      </div>
    </div>
  );
};
export default MonthlyVariable;
