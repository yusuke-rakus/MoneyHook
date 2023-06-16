import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/MonthlyFixed.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import { rootURI, timeoutRange } from "../env/env";
import { LoadFetchError } from "../components/FetchError";
import { getJST } from "../components/GetJST";
import FixedCategoryGroup from "../components/FixedCategoryGroup";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import Typography from "@mui/material/Typography";
import Sidebar from "../components/Sidebar";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";
import Collapse from "@mui/material/Collapse";
import CloseIcon from "@mui/icons-material/Close";

const MonthlyFixed = (props) => {
  const { themeColor } = props;
  const [isLoading, setLoading] = useState(false);
  const [cookie] = useCookies();
  const navigate = useNavigate();

  /** 今月 */
  const [sysDate, setSysDate] = useState(getJST(new Date()));
  sysDate.setDate(1);

  const [totalFixedIncome, setTotalFixedIncome] = useState(0);
  const [fixedIncomeCategoryData, setFixedIncomeCategoryData] = useState([]);

  const [totalFixedSpending, setTotalFixedSpending] = useState(0);
  const [fixedSpendingCategoryData, setFixedSpendingCategoryData] = useState(
    []
  );
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
  const getInit = (month) => {
    setLoading(true);
    // 月別固定収入の取得
    fetch(`${rootURI}/transaction/getMonthlyFixedIncome`, {
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
          setFixedIncomeCategoryData(data.monthlyFixedList);
          setTotalFixedIncome(data.disposableIncome);
        }
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        LoadFetchError(setLoading, setBanner);
      });

    // 月別固定支出の取得
    fetch(`${rootURI}/transaction/getMonthlyFixedSpending`, {
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
          setFixedSpendingCategoryData(data.monthlyFixedList);
          setTotalFixedSpending(data.disposableIncome);
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
  }, [setFixedIncomeCategoryData, setFixedSpendingCategoryData]);

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

          {isLoading === false &&
            fixedIncomeCategoryData.length === 0 &&
            fixedSpendingCategoryData.length === 0 && (
              <div className="dataNotFound">
                <p>データが登録されていません</p>
                <p>家計簿を入力して記録を開始しましょう</p>
              </div>
            )}

          {/* 固定費データ */}
          <div className="fixedDataArea">
            {isLoading ? (
              <>
                <div className="loading fixedLoadingData"></div>
                <div className="loading fixedLoadingData"></div>
                <div className="loading fixedLoadingData"></div>
                <div className="loading fixedLoadingData"></div>
                <div className="loading fixedLoadingData"></div>
              </>
            ) : (
              <>
                <FixedCategoryGroup
                  fixedCategoryData={fixedIncomeCategoryData}
                />
                <FixedCategoryGroup
                  fixedCategoryData={fixedSpendingCategoryData}
                />

                {/* 合計 */}
                {isLoading === false &&
                  fixedIncomeCategoryData.length !== 0 &&
                  fixedSpendingCategoryData.length !== 0 && (
                    <Accordion>
                      <AccordionSummary>
                        <Typography className="totalValueArea">
                          <span className="totalValue">
                            <span>合計</span>
                            <span>{`¥${Math.abs(
                              totalFixedIncome
                            ).toLocaleString()}`}</span>
                            <span>
                              {`¥${Math.abs(
                                totalFixedSpending
                              ).toLocaleString()}`}
                            </span>
                          </span>
                        </Typography>
                      </AccordionSummary>
                    </Accordion>
                  )}
              </>
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
export default MonthlyFixed;
