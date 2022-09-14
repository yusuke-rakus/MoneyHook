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
  const [sysDate, setSysDate] = useState(new Date("2022-06-01"));
  sysDate.setDate(1);

  const [totalFixedIncome, setTotalFixedIncome] = useState(0);
  const [fixedIncomeCategoryData, setFixedIncomeCategoryData] = useState([]);

  const [totalFixedSpending, setTotalFixedSpending] = useState(0);
  const [fixedSpendingCategoryData, setFixedSpendingCategoryData] = useState(
    []
  );

  /** API関連 */
  const rootURI = "http://localhost:8080";

  const getInit = (month) => {
    // 月別固定収入の取得
    fetch(`${rootURI}/transaction/getMonthlyFixedIncome`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        month: month,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          setFixedIncomeCategoryData(data.monthlyFixedList);
          setTotalFixedIncome(data.disposableIncome);
        }
      });

    // 月別固定支出の取得
    fetch(`${rootURI}/transaction/getMonthlyFixedSpending`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        month: month,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          setFixedSpendingCategoryData(data.monthlyFixedList);
          setTotalFixedSpending(data.disposableIncome);
        }
      });
  };

  /** 前月データを取得 */
  const getPastMonth = () => {
    let tempDate = new Date(sysDate);
    tempDate.setMonth(tempDate.getMonth() - 1);
    setSysDate(tempDate);
    getInit(tempDate);
  };

  /** 次月データを取得 */
  const getForwardMonth = () => {
    let tempDate = new Date(sysDate);
    tempDate.setMonth(tempDate.getMonth() + 1);
    setSysDate(tempDate);
    getInit(tempDate);
  };

  useEffect(() => {
    getInit(sysDate);
  }, [setFixedIncomeCategoryData, setFixedSpendingCategoryData]);

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <span onClick={getPastMonth}>
          <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        </span>
        <span>{sysDate.getMonth() + 1}月</span>
        <span onClick={getForwardMonth}>
          <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
        </span>
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
