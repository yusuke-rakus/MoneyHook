import React, { useEffect, useState } from "react";
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

  const [totalFixedIncome, setTotalFixedIncome] = useState(0);
  const [fixedIncomeCategoryData, setFixedIncomeCategoryData] = useState([]);

  const [totalFixedSpending, setTotalFixedSpending] = useState(0);
  const [fixedSpendingCategoryData, setFixedSpendingCategoryData] = useState(
    []
  );

  /** API関連 */
  const rootURI = "http://localhost:8080";

  // 月別固定収入の取得
  useEffect(() => {
    fetch(`${rootURI}/transaction/getMonthlyFixedIncome`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        month: "2022-06-01",
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          setFixedIncomeCategoryData(data.monthlyFixedList);
          setTotalFixedIncome(data.disposableIncome);
        }
      });
  }, [setFixedSpendingCategoryData]);

  // 月別固定支出の取得
  useEffect(() => {
    fetch(`${rootURI}/transaction/getMonthlyFixedSpending`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        month: "2022-06-01",
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          setFixedSpendingCategoryData(data.monthlyFixedList);
          setTotalFixedSpending(data.disposableIncome);
        }
      });
  }, [setFixedSpendingCategoryData]);

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        <span>{date.getMonth() + 1}月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
      </div>

      {/* 可処分所得 */}
      <div className="monthlyFixedTitleArea">
        <span>可処分所得額</span>
        <span
          style={
            totalFixedSpending + totalFixedIncome >= 0
              ? { color: "#1B5E20" }
              : { color: "#B71C1C" }
          }
        >
          {(totalFixedSpending + totalFixedIncome).toLocaleString()}
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
                <span>{`¥${Math.abs(totalFixedIncome).toLocaleString()}`}</span>
                <span>
                  {`¥${Math.abs(totalFixedSpending).toLocaleString()}`}
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
