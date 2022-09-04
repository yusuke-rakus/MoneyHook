import React, { useState } from "react";
import "./page_CSS/Home.css";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { Pie } from "react-chartjs-2";
import HomeAccodion from "../components/HomeAccodion";
import ModalWindow from "../components/window/ModalWindow";

const Home = () => {
  /** 今月 */
  let date = new Date();
  let formatday = `${date.getFullYear()}-${date.getMonth() + 1}-1`;

  /** 収支合計 */
  const monthlyTotalAmount = -1000;

  /** アコーディオンデータ */
  const homeAccodionDataList = [
    {
      categoryName: "住宅",
      categoryAmount: -100000,
      subCategoryList: [
        {
          subCategoryName: "家賃",
          subCategoryAmount: -100000,
        },
      ],
    },
    {
      categoryName: "食費",
      categoryAmount: -75000,
      subCategoryList: [
        {
          subCategoryName: "スーパー",
          subCategoryAmount: -45000,
        },
        {
          subCategoryName: "外食",
          subCategoryAmount: -30000,
        },
      ],
    },
    {
      categoryName: "趣味",
      categoryAmount: -70000,
      subCategoryList: [
        {
          subCategoryName: "旅行",
          subCategoryAmount: -50000,
        },
        {
          subCategoryName: "温泉",
          subCategoryAmount: -10000,
        },
        {
          subCategoryName: "スポーツ",
          subCategoryAmount: -10000,
        },
      ],
    },
    {
      categoryName: "ショッピング",
      categoryAmount: -50000,
      subCategoryList: [
        {
          subCategoryName: "調理器具",
          subCategoryAmount: -50000,
        },
      ],
    },
    {
      categoryName: "光熱費",
      categoryAmount: -20000,
      subCategoryList: [
        {
          subCategoryName: "電気代",
          subCategoryAmount: -20000,
        },
      ],
    },
    {
      categoryName: "返済",
      categoryAmount: -10000,
      subCategoryList: [
        {
          subCategoryName: "奨学金",
          subCategoryAmount: -10000,
        },
      ],
    },
  ];

  /** カラーリスト */
  const dataColorList = [
    "#e57373",
    "#29b6f6",
    "#81c784",
    "#f9a825",
    "#9575cd",
    "#bdbdbd",
  ];

  /** グラフデータ */
  const data = {
    labels: homeAccodionDataList.map((e) => e.categoryName),
    datasets: [
      {
        data: homeAccodionDataList.map((e) => Math.abs(e.categoryAmount)),
        backgroundColor: dataColorList,
        borderWidth: 0,
      },
    ],
  };

  /** グラフオプション */
  const option = {
    plugins: {
      legend: {
        display: false,
      },
    },
  };

  return (
    <div className="container">
      {/* 月 */}
      <div className="month">
        <ArrowBackIosNewIcon fontSize="large" className="switchMonthButton" />
        <span>{date.getMonth() + 1}月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
      </div>

      {/* 収支合計 */}
      <div className="monthlyTotalAmountTitleArea">
        <span>変動費合計</span>
        <span
          style={
            monthlyTotalAmount >= 0
              ? { color: "#1B5E20" }
              : { color: "#B71C1C" }
          }
        >
          {monthlyTotalAmount.toLocaleString()}
        </span>
      </div>
      <div className="dataArea">
        <div className="accodionDataArea">
          <HomeAccodion
            homeAccodionDataList={homeAccodionDataList}
            colorList={dataColorList}
          />
        </div>

        {/* グラフ */}
        <div className="pieGraph">
          <Pie data={data} options={option} className="pieGraph" />
        </div>
      </div>

      {/* 貯金追加ボタン */}
      <div className="addTransactionArea">
        <ModalWindow />
      </div>
    </div>
  );
};
export default Home;
