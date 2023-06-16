import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/MonthlyVariable.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import { rootURI, timeoutRange } from "../env/env";
import { LoadFetchError } from "../components/FetchError";
import { getJST } from "../components/GetJST";
import VariableCategoryGroup from "../components/VariableCategoryGroup";
import Sidebar from "../components/Sidebar";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";
import Collapse from "@mui/material/Collapse";
import CloseIcon from "@mui/icons-material/Close";

const MonthlyVariable = (props) => {
  const { themeColor } = props;
  const [cookie] = useCookies();
  const navigate = useNavigate();

  const [isLoading, setLoading] = useState(false);
  /** 今月 */
  const [sysDate, setSysDate] = useState(getJST(new Date()));
  sysDate.setDate(1);

  const [monthlyTotalVariable, setMonthlyTotalVariable] = useState(0);

  const [variableCategoryData, setVariableCategoryData] = useState([]);

  const [banner, setBanner] = useState({
    banner: false,
    bannerMessage: "",
    bannerType: "",
  });
  useEffect(() => {
    setTimeout(() => {
      setBanner({
        ...banner,
        banner: false,
      });
    }, timeoutRange);
  }, [banner]);

  /** API関連 */
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
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        LoadFetchError(setLoading, setBanner);
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
    if (sysDate < new Date()) {
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

        {/* バーナー */}
        <div className="bannerArea">
          <Collapse in={banner.banner}>
            <Alert
              severity={banner.bannerType}
              action={
                <IconButton
                  aria-label="close"
                  color="inherit"
                  size="small"
                  onClick={() => {
                    setBanner(false);
                  }}
                >
                  <CloseIcon fontSize="inherit" />
                </IconButton>
              }
              sx={{ mb: 1 }}
            >
              {banner.bannerMessage}
            </Alert>
          </Collapse>
        </div>
      </div>
    </>
  );
};
export default MonthlyVariable;
