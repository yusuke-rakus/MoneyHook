import React from "react";
import "./page_CSS/Home.css";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { Pie } from "react-chartjs-2";
import HomeAccodion from "../components/HomeAccodion";

const Home = () => {
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
    "#EF5350",
    "#03A9F4",
    "#66BB6A",
    "#F57F17",
    "#7e57c2",
    "#9E9E9E",
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
        <span>6月</span>
        <ArrowForwardIosIcon fontSize="large" className="switchMonthButton" />
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
    </div>
  );
};
export default Home;
