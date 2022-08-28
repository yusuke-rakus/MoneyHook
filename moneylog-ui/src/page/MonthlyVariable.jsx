import React from "react";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import "./page_CSS/MonthlyVariable.css";
import VariableCategoryGroup from "../components/VariableCategoryGroup";

const MonthlyVariable = () => {
  const variableCategoryData = [
    {
      categoryName: "趣味",
      categoryAmount: 100000,
      variableSubCategoryData: [
        {
          subCategoryName: "スポーツ",
          subCategoryAmount: 100000,
          transactionList: [
            { transactionName: "ゲートボール", transactionAmount: 100000 },
            { transactionName: "少林寺拳法", transactionAmount: 100000 },
          ],
        },
        {
          subCategoryName: "スポーツ",
          subCategoryAmount: 100000,
          transactionList: [
            { transactionName: "ゲートボール", transactionAmount: 100000 },
            { transactionName: "少林寺拳法", transactionAmount: 100000 },
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
        <span>6月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
      </div>

      {/* 変動費合計 */}
      <div className="monthlyVariableTitleArea">
        <span>変動費合計</span>
        <span>nnn,nnn</span>
      </div>

      {/* 変動費データ */}
      <div className="variableDataArea">
        <VariableCategoryGroup variableCategoryData={variableCategoryData} />
      </div>
    </div>
  );
};
export default MonthlyVariable;
