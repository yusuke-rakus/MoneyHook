import React from "react";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import "./page_CSS/MonthlyFixed.css";
import FixedCategoryGroup from "../components/FixedCategoryGroup";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const MonthlyFixed = () => {
  const monthlyTotalFixed = 100000;

  const totalFixedIncome = 250000;
  const fixedIncomeCategoryData = [
    {
      categoryName: "給与",
      categoryAmount: 250000,
      fixedTransactionData: [
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

  const totalFixedSpending = 250000;
  const fixedSpendingCategoryData = [
    {
      categoryName: "家賃",
      categoryAmount: -100000,
      fixedTransactionData: [
        {
          transactionName: "電気代",
          transactionAmount: -100000,
        },
      ],
    },
    {
      categoryName: "水道光熱費",
      categoryAmount: -21000,
      fixedTransactionData: [
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
      categoryAmount: -20000,
      fixedTransactionData: [
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
        <span>6月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
      </div>

      {/* 変動費合計 */}
      <div className="monthlyFixedTitleArea">
        <span>変動費合計</span>
        <span>{monthlyTotalFixed.toLocaleString()}</span>
      </div>

      {/* 固定費データ */}
      <div className="fixedDataArea">
        <FixedCategoryGroup fixedCategoryData={fixedIncomeCategoryData} />
        <FixedCategoryGroup fixedCategoryData={fixedSpendingCategoryData} />
        <Accordion>
          <AccordionSummary>
            <Typography className="totalValueArea">
              <div className="totalValue">
                <span>合計</span>
                <span>{"¥" + Math.abs(totalFixedIncome).toLocaleString()}</span>
                <span>{"¥" + Math.abs(totalFixedIncome).toLocaleString()}</span>
              </div>
            </Typography>
          </AccordionSummary>
        </Accordion>
      </div>
    </div>
  );
};
export default MonthlyFixed;
