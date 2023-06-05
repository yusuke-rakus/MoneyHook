import React, { useEffect, useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/AddSavingBox.css";
/** 自作コンポーネント */
import { rootURI } from "../../env/env";
import {
  LoadFetchErrorWithSeparateBanner,
  PostErrorWithSeparateBanner,
} from "../FetchError";
import { getJST } from "../GetJST";
/** 外部コンポーネント */
import CloseIcon from "@mui/icons-material/Close";
import {
  FormControl,
  Select,
  MenuItem,
  TextField,
  Button,
  CircularProgress,
  Autocomplete,
  Collapse,
  Alert,
  IconButton,
} from "@mui/material";
import { useCookies } from "react-cookie";

const AddSavingBox = (props) => {
  const {
    title,
    setAddSavingStatus,
    saving,
    setSaving,
    getInit,
    month,
    setBanner,
    setBannerMessage,
    setBannerType,
  } = props;

  const [isLoading, setLoading] = useState(false);
  const [cookie] = useCookies();
  const [label, setLabel] = useState({
    savingName: { message: "名称", status: false },
    savingAmount: { message: "金額", status: false },
  });
  const [modalBanner, setModalBanner] = useState({
    banner: false,
    bannerMessage: "",
    bannerType: "success",
  });

  // 振り分け処理
  const [distributionList, setDistributionList] = useState([]);
  const changeDistribution = (e) => {
    setSaving({ ...saving, savingTargetId: e.target.value });
  };

  /** 貯金日がなければ当日をセット */
  if (!saving.savingDate) {
    setSaving({ ...saving, savingDate: getJST(new Date()) });
  }

  /** 金額表示処理 */
  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    if (tempNum > 9999999) {
      setLabel((label) => ({
        ...label,
        savingAmount: {
          message: "¥9,999,999以内",
          status: true,
        },
      }));
    } else {
      setLabel((label) => ({
        ...label,
        savingAmount: {
          message: "金額",
          status: false,
        },
      }));
    }
    setSaving({
      ...saving,
      savingAmount: tempNum,
    });
  };

  /** 取引名入力 */
  const changeSavingName = (e) => {
    if (e.target.value.length > 32) {
      setLabel((label) => ({
        ...label,
        savingName: {
          message: "32文字以内",
          status: true,
        },
      }));
    } else {
      setLabel((label) => ({
        ...label,
        savingName: {
          message: "名称",
          status: false,
        },
      }));
    }
    setSaving({ ...saving, savingName: e.target.value });
  };

  const selectSavingName = (e, v) => {
    setSaving({ ...saving, savingName: v });
  };

  /** 日付処理 */
  const setYear = (e) => {
    let d = new Date(saving.savingDate);
    d.setFullYear(e.target.value);
    setSaving({ ...saving, savingDate: new Date(d) });
  };
  const setMonth = (e) => {
    let d = new Date(saving.savingDate);
    d.setMonth(e.target.value);
    setSaving({ ...saving, savingDate: new Date(d) });
  };
  const setDay = (e) => {
    let d = new Date(saving.savingDate);
    d.setDate(e.target.value);
    setSaving({ ...saving, savingDate: new Date(d) });
  };

  /** 閉じる処理 */
  const closeAddSavingWindow = () => {
    setSaving({});
    setAddSavingStatus(false);
  };

  /** 貯金名レコメンドリスト */
  const [recommendList, setRecommendList] = useState([]);

  /** API関連 */
  /** 登録処理 */
  const register = () => {
    // 未入力チェック
    if (!saving.savingName || !saving.savingAmount) {
      setLabel((label) => ({
        ...label,
        savingName: {
          message: !saving.savingName ? "未入力" : label.savingName.message,
          status: !saving.savingName ? true : label.savingName.status,
        },
        savingAmount: {
          message: !saving.savingAmount ? "未入力" : label.savingAmount.message,
          status: !saving.savingAmount ? true : label.savingAmount.status,
        },
      }));
      return;
    }

    if (saving.savingId === void 0) {
      // 登録処理
      addSaving();
    } else {
      // 編集処理
      editSaving();
    }
    setBanner(true);
  };

  /** 貯金追加 */
  const addSaving = () => {
    setLoading(true);
    fetch(`${rootURI}/saving/addSaving`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        savingDate: saving.savingDate,
        savingName: saving.savingName,
        savingAmount: saving.savingAmount,
        savingTargetId: saving.savingTargetId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功
          setBannerMessage(data.message);
          setBannerType(data.status);
          closeAddSavingWindow();
          getInit(month);
        } else {
          // 失敗
          setModalBanner({
            banner: true,
            bannerMessage: data.message,
            bannerType: data.status,
          });
        }
      })
      .finally(() => {
        setLoading(false);
      })
      .catch(() =>
        PostErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType
        )
      );
  };

  /** 貯金編集 */
  const editSaving = () => {
    setLoading(true);
    fetch(`${rootURI}/saving/editSaving`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        savingId: saving.savingId,
        savingDate: saving.savingDate,
        savingName: saving.savingName,
        savingAmount: saving.savingAmount,
        savingTargetId: saving.savingTargetId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功
          setBannerMessage(data.message);
          setBannerType(data.status);
          closeAddSavingWindow();
          getInit(month);
        } else {
          // 失敗
        }
      })
      .finally(() => {
        setLoading(false);
      })
      .catch(() =>
        PostErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType
        )
      );
  };

  /** 貯金削除 */
  const deleteSaving = () => {
    setLoading(true);
    fetch(`${rootURI}/saving/deleteSaving`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        savingId: saving.savingId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功
        } else {
          // 失敗
        }
        setBannerMessage(data.message);
        setBannerType(data.status);
      })
      .finally(() => {
        setLoading(false);
        closeAddSavingWindow();
        getInit(month);
        setBanner(true);
      })
      .catch(() =>
        PostErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType
        )
      );
  };

  /** 目標一覧取得処理 */
  const getSavingTargetList = () => {
    fetch(`${rootURI}/savingTarget/getSavingTargetList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功
          setDistributionList(data.savingTarget);
        } else {
          // 失敗
        }
      })
      .catch(() => {
        LoadFetchErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType
        );
        closeAddSavingWindow();
      });
  };

  /** 貯金名レコメンド取得 */
  const getFrequentSavingName = () => {
    fetch(`${rootURI}/saving/getFrequentSavingName`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功
          setRecommendList(data.savingList.map((data) => data.savingName));
        } else {
          // 失敗
        }
      })
      .catch(() => {
        LoadFetchErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType
        );
        closeAddSavingWindow();
      });
  };

  useEffect(() => {
    getSavingTargetList();
    getFrequentSavingName();
  }, [setDistributionList, setRecommendList]);

  return (
    <>
      <div className="modal-window">
        {/* バーナー */}

        <Collapse in={modalBanner.banner}>
          <Alert
            severity={modalBanner.bannerType}
            action={
              <IconButton
                aria-label="close"
                color="inherit"
                size="small"
                onClick={() => {
                  setModalBanner({
                    ...modalBanner,
                    banner: false,
                  });
                }}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>
            }
            sx={{ mb: 1 }}
          >
            {modalBanner.bannerMessage}
          </Alert>
        </Collapse>

        <CloseIcon
          onClick={closeAddSavingWindow}
          style={{ cursor: "pointer", color: "#a9a9a9" }}
          className="close-button"
        />
        <h3 className="modal-title">{title}</h3>

        {/* 日付入力 */}
        <div className="input-date-box">
          <span className="input-span">日付</span>
          <div className="date-group">
            <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
              <Select
                value={new Date(saving.savingDate).getFullYear()}
                onChange={setYear}
              >
                {[...Array(20)].map((v, i) => {
                  return (
                    <MenuItem
                      key={i}
                      value={getJST(new Date()).getFullYear() - i}
                    >
                      {getJST(new Date()).getFullYear() - i}
                    </MenuItem>
                  );
                })}
              </Select>
            </FormControl>
            <span>年</span>
            <FormControl sx={{ m: 1, minWidth: 60 }} size="small">
              <Select
                value={new Date(saving.savingDate).getMonth()}
                onChange={setMonth}
              >
                {[...Array(12)].map((v, i) => {
                  return (
                    <MenuItem key={i} value={i}>
                      {i + 1}
                    </MenuItem>
                  );
                })}
              </Select>
            </FormControl>
            <span>月</span>
            <FormControl sx={{ m: 1, minWidth: 60 }} size="small">
              <Select
                value={new Date(saving.savingDate).getDate()}
                onChange={setDay}
              >
                {[...Array(31)].map((v, i) => {
                  return (
                    <MenuItem key={i} value={i + 1}>
                      {i + 1}
                    </MenuItem>
                  );
                })}
              </Select>
            </FormControl>
            <span>日</span>
          </div>
        </div>

        {/* 名称 */}
        <div className="input-name-box">
          <span
            className="input-span"
            style={label.savingName.status ? { color: "#c62828" } : {}}
          >
            {label.savingName.message}
          </span>
          <Autocomplete
            freeSolo
            options={recommendList}
            defaultValue={saving && saving.savingName}
            onChange={selectSavingName}
            renderInput={(params) => (
              <TextField
                {...params}
                variant="standard"
                autoComplete="off"
                fullWidth={true}
                error={label.savingName.status}
                onChange={changeSavingName}
                InputProps={{
                  ...params.InputProps,
                  style: {
                    paddingRight: "20px",
                    paddingLeft: "20px",
                    fontSize: "20px",
                    color: "#424242",
                  },
                }}
              />
            )}
          />
        </div>

        {/* 貯金額 */}
        <div className="amount-group">
          <span
            className="input-span"
            style={label.savingAmount.status ? { color: "#c62828" } : {}}
          >
            {label.savingAmount.message}
          </span>
          <div className="saving-amount">
            <TextField
              variant="standard"
              autoComplete="off"
              error={label.savingAmount.status}
              fullWidth={true}
              inputProps={{
                style: {
                  textAlign: "right",
                  paddingRight: "20px",
                  paddingLeft: "20px",
                  fontSize: "20px",
                  color: "#424242",
                },
              }}
              value={
                isNaN(saving.savingAmount)
                  ? ""
                  : Math.abs(saving.savingAmount).toLocaleString()
              }
              onChange={changeAmount}
            />
            <span className="input-span">円</span>
          </div>
        </div>

        {/* 振り分け選択 */}
        <div className="distribution-group">
          <span className="input-span">振り分ける</span>
          <FormControl size="small" sx={{ mt: "3px", minWidth: "250px" }}>
            <Select value={saving.savingTargetId} onChange={changeDistribution}>
              <MenuItem
                value={null}
                sx={{ color: "#616161", fontSize: "13px" }}
              >
                未分類
              </MenuItem>
              {distributionList.map((distributionItem, i) => {
                return (
                  <MenuItem key={i} value={distributionItem.savingTargetId}>
                    {distributionItem.savingTargetName}
                  </MenuItem>
                );
              })}
            </Select>
          </FormControl>
        </div>

        {/* 登録ボタン */}
        <Button onClick={register} variant="contained" disabled={isLoading}>
          {isLoading ? <CircularProgress size={20} /> : "登録"}
        </Button>

        {/* 削除ボタン */}
        {saving.savingId && (
          <div className="deleteButton">
            <Button
              onClick={deleteSaving}
              disabled={isLoading}
              size="small"
              sx={{ color: "#9e9e9e" }}
            >
              削除
            </Button>
          </div>
        )}
      </div>
    </>
  );
};

export default AddSavingBox;
