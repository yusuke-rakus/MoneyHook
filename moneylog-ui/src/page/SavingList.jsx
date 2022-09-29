import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/SavingList.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import SavingListData from "../components/SavingListData";
import AddSavingBox from "../components/window/AddSavingBox";
import HouseholdBudgetButton from "../components/HouseholdBudgetButton";
import BlurView from "../components/window/BlurView";
/** 外部コンポーネント */
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { CSSTransition } from "react-transition-group";
import Sidebar from "../components/Sidebar";
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";
import Collapse from "@mui/material/Collapse";
import CloseIcon from "@mui/icons-material/Close";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

const SavingList = (props) => {
  const { themeColor } = props;
  const [isLoading, setLoading] = useState(false);
  const [cookie, setCookie] = useCookies();
  const navigate = useNavigate();

  /** バナーのステータス */
  const [banner, setBanner] = useState(false);
  const [bannerMessage, setBannerMessage] = useState("");
  const [bannerType, setBannerType] = useState("success");

  /** 今月 */
  const [sysDate, setSysDate] = useState(new Date("2022-06-01"));
  sysDate.setDate(1);

  const [AddSavingStatus, setAddSavingStatus] = useState(false);

  const [totalSavingAmount, setTotalSavingAmount] = useState(0);

  const [savingDataList, setSavingDataList] = useState([]);

  const [saving, setSaving] = useState({});

  const [savingTitle, setSavingTitle] = useState("貯金を追加");

  /** API関連 */
  const rootURI = "http://localhost:8080";

  const getInit = (month) => {
    setLoading(true);
    // 月別固定収入の取得
    fetch(`${rootURI}/saving/getMonthlySavingData`, {
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
        if (data.status == "success") {
          setSavingDataList(data.savingList);
          setTotalSavingAmount(
            data.savingList.reduce(
              (sum, element) => sum + element.savingAmount,
              0
            )
          );
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
    let tempDate = new Date(sysDate);
    tempDate.setMonth(tempDate.getMonth() + 1);
    setSysDate(tempDate);
    getInit(tempDate);
  };

  useEffect(() => {
    !cookie.userId && navigate("/login");
    getInit(sysDate);
  }, [setSavingDataList]);

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

          {/* 今月の貯金額 */}
          <div className="monthlyTotalSaving">
            <span>今月の貯金額</span>
            <span className="totalSavingAmount">
              {totalSavingAmount.toLocaleString()}
            </span>
          </div>

          {/* 貯金データ */}
          {isLoading ? (
            <>
              <div className="savingList loadingSavingData loading"></div>
              <div className="savingList loadingSavingData loading"></div>
              <div className="savingList loadingSavingData loading"></div>
              <div className="savingList loadingSavingData loading"></div>
              <div className="savingList loadingSavingData loading"></div>
            </>
          ) : (
            <div className="savingList">
              {savingDataList.map((data, i) => {
                return (
                  <SavingListData
                    key={i}
                    setAddSavingStatus={setAddSavingStatus}
                    saving={data}
                    setSaving={setSaving}
                    setSavingTitle={setSavingTitle}
                  />
                );
              })}
            </div>
          )}

          {/* バーナー */}
          <div className="bannerArea">
            <Collapse in={banner}>
              <Alert
                severity={bannerType}
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
                {bannerMessage}
              </Alert>
            </Collapse>
          </div>

          {/* 貯金追加ボタン */}
          <div className="addSavingArea">
            <HouseholdBudgetButton
              openWindow={setAddSavingStatus}
              buttonText={"貯金"}
              setData={setSaving}
              setSavingTitle={setSavingTitle}
            />
          </div>

          {/* 貯金追加ウィンドウ */}
          <BlurView
            status={AddSavingStatus}
            setStatus={setAddSavingStatus}
            setObject={setSaving}
          />
          <CSSTransition
            in={AddSavingStatus}
            timeout={100}
            unmountOnExit
            classNames="Modal-show"
          >
            <AddSavingBox
              title={savingTitle}
              setAddSavingStatus={setAddSavingStatus}
              saving={saving}
              setSaving={setSaving}
              getInit={getInit}
              month={sysDate}
              setBanner={setBanner}
              setBannerMessage={setBannerMessage}
              setBannerType={setBannerType}
            />
          </CSSTransition>
        </div>
      </div>
    </>
  );
};
export default SavingList;
