import React, { useEffect, useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/AddSavingBox.css";
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
} from "@mui/material";

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

  // 振り分け処理
  const [distributionList, setDistributionList] = useState([]);
  const changeDistribution = (e) => {
    setSaving({ ...saving, savingTargetId: e.target.value });
  };

  /** 貯金日がなければ当日をセット */
  if (!saving.savingDate) {
    setSaving({ ...saving, savingDate: new Date() });
  }

  /** 金額表示処理 */
  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    setSaving({
      ...saving,
      savingAmount: tempNum,
    });
  };

  /** 取引名入力 */
  const changeSavingName = (e) => {
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
  const rootURI = "http://localhost:8080";

  /** 登録処理 */
  const register = () => {
    if (saving.savingId == void 0) {
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
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        savingDate: saving.savingDate,
        savingName: saving.savingName,
        savingAmount: saving.savingAmount,
        savingTargetId: saving.savingTargetId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
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
      });
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
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        savingId: saving.savingId,
        savingDate: saving.savingDate,
        savingName: saving.savingName,
        savingAmount: saving.savingAmount,
        savingTargetId: saving.savingTargetId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
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
      });
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
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        savingId: saving.savingId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
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
      });
  };

  /** 目標一覧取得処理 */
  const getSavingTargetList = () => {
    fetch(`${rootURI}/savingTarget/getSavingTargetList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功
          setDistributionList(data.savingTarget);
        } else {
          // 失敗
        }
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
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功
          setRecommendList(data.savingList.map((data) => data.savingName));
        } else {
          // 失敗
        }
      });
  };

  useEffect(() => {
    getSavingTargetList();
    getFrequentSavingName();
  }, [setDistributionList, setRecommendList]);

  return (
    <>
      <div className="modal-window">
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
                    <MenuItem key={i} value={new Date().getFullYear() - i}>
                      {new Date().getFullYear() - i}
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
          <span className="input-span">名称</span>
          <Autocomplete
            freeSolo
            options={recommendList}
            defaultValue={saving && saving.savingName}
            onChange={selectSavingName}
            renderInput={(params) => (
              <TextField
                {...params}
                variant="standard"
                fullWidth={true}
                autoComplete="off"
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
          <span className="input-span">金額</span>
          <div className="saving-amount">
            <TextField
              id="standard-basic"
              variant="standard"
              autoComplete="off"
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
