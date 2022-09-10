import React from "react";
/** CSS */
import "./page_CSS/MonthlyFixed.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import FixedCategoryGroup from "../components/FixedCategoryGroup";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import Typography from "@mui/material/Typography";

const MonthlyFixed = () => {
  /** 今月 */
  let date = new Date();
  let formatday = `${date.getFullYear()}-${date.getMonth() + 1}-1`;

  const monthlyTotalFixed = 100000;

  const totalFixedIncome = 250000;
  const fixedIncomeCategoryData = [
    {
      categoryName: "給与",
      totalCategoryAmount: 250000,
      transactionList: [
        {
          transactionName: "会社給与",
          transactionAmount: 200000,
        },
        {
          transactionName: "副業収入",
          transactionAmount: 50000,
        },
      ],
    },
  ];

  const totalFixedSpending = -141000;
  const fixedSpendingCategoryData = [
    {
      categoryName: "家賃",
      totalCategoryAmount: -100000,
      transactionList: [
        {
          transactionName: "電気代",
          transactionAmount: -100000,
        },
      ],
    },
    {
      categoryName: "水道光熱費",
      totalCategoryAmount: -21000,
      transactionList: [
        {
          transactionName: "電気代",
          transactionAmount: -10000,
        },
        {
          transactionName: "ガス代",
          transactionAmount: -10000,
        },
        {
          transactionName: "水道代",
          transactionAmount: -1000,
        },
      ],
    },
    {
      categoryName: "返済",
      totalCategoryAmount: -20000,
      transactionList: [
        {
          transactionName: "奨学金",
          transactionAmount: -20000,
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
      <div className="monthlyFixedTitleArea">
        <span>変動費合計</span>
        <span
          style={
            monthlyTotalFixed >= 0 ? { color: "#1B5E20" } : { color: "#B71C1C" }
          }
        >
          {monthlyTotalFixed.toLocaleString()}
        </span>
      </div>

      {/* 固定費データ */}
      <div className="fixedDataArea">
        <FixedCategoryGroup fixedCategoryData={fixedIncomeCategoryData} />
        <FixedCategoryGroup fixedCategoryData={fixedSpendingCategoryData} />
        <Accordion>
          <AccordionSummary>
            <Typography className="totalValueArea">
              <span className="totalValue">
                <span>合計</span>
                <span>{"¥" + Math.abs(totalFixedIncome).toLocaleString()}</span>
                <span>
                  {"¥" + Math.abs(totalFixedSpending).toLocaleString()}
                </span>
              </span>
            </Typography>
          </AccordionSummary>
        </Accordion>
      </div>
    </div>
  );
};
export default MonthlyFixed;
