import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/MonthlyVariable.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import VariableCategoryGroup from "../components/VariableCategoryGroup";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import Sidebar from "../components/Sidebar";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

const MonthlyVariable = (props) => {
  const { themeColor } = props;
  const [cookie] = useCookies();
  const navigate = useNavigate();

  const [isLoading, setLoading] = useState(false);
  /** 今月 */
  const [sysDate, setSysDate] = useState(new Date("2022-06-01"));
  sysDate.setDate(1);

  const [monthlyTotalVariable, setMonthlyTotalVariable] = useState(0);

  const [variableCategoryData, setVariableCategoryData] = useState([]);

  /** API関連 */
  const rootURI = "http://localhost:8080";

  // 当月の変動費を取得
  const getInit = (month) => {
    setLoading(true);
    fetch(`${rootURI}/transaction/getMonthlyVariableData`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        month: month,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          setVariableCategoryData(data.monthlyVariableList);
          setMonthlyTotalVariable(data.totalVariable);
          setLoading(false);
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
    if (sysDate.getMonth() < new Date().getMonth()) {
      let tempDate = new Date(sysDate);
      tempDate.setMonth(tempDate.getMonth() + 1);
      setSysDate(tempDate);
      getInit(tempDate);
    }
  };

  useEffect(() => {
    !cookie.userId && navigate("/login");
    getInit(sysDate);
  }, [setVariableCategoryData]);

  return (
    <>
      <Sidebar themeColor={themeColor} />

      <div className="homeArea">
        <div className="container">
          {/* 月 */}
          <div className="month">
            <span onClick={getPastMonth}>
              <ArrowBackIosNewIcon
                fontSize="large"
                className="switchMonthButton"
              />
            </span>
            <span>{sysDate.getMonth() + 1}月</span>
            <span onClick={getForwardMonth}>
              <ArrowForwardIosIcon
                fontSize="large"
                className="switchMonthButton"
              />
            </span>
          </div>

          {/* 変動費合計 */}
          <div className="monthlyVariableTitleArea">
            <span>変動費合計</span>
            <span>{Math.abs(monthlyTotalVariable).toLocaleString()}</span>
          </div>

          {isLoading === false && variableCategoryData.length === 0 && (
            <div className="dataNotFound">
              <p>データが登録されていません</p>
              <p>家計簿を入力して記録を開始しましょう</p>
            </div>
          )}

          {/* 変動費データ */}
          <div className="variableDataArea">
            {isLoading ? (
              <>
                <div className="loading variableData"></div>
                <div className="loading variableData"></div>
                <div className="loading variableData"></div>
                <div className="loading variableData"></div>
                <div className="loading variableData"></div>
              </>
            ) : (
              <VariableCategoryGroup
                variableCategoryData={variableCategoryData}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default MonthlyVariable;
