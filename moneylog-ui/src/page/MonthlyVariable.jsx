import React from "react";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import "./page_CSS/MonthlyVariable.css";

const MonthlyVariable = () => {
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
      <div className="variableDataArea"></div>
    </div>
  );
};
export default MonthlyVariable;
