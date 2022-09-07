import React, { useState } from "react";
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
} from "@mui/material";

const AddSavingBox = (props) => {
  // 振り分け処理
  const [distribution, setDistribution] = useState("");
  const distributionList = ["M2 MacBookAir", "沖縄旅行"];
  const changeDistribution = (e) => {
    setDistribution(e.target.value);
  };

  // 以下必須
  const { title, setAddSavingStatus, saving, setSaving } = props;

  /** 貯金日がなければ当日をセット */
  if (!saving.savingDate) {
    setSaving({ ...saving, savingDate: new Date() });
  }

  /** 閉じる処理 */
  const closeAddSavingWindow = () => {
    setSaving({});
    setAddSavingStatus(false);
  };

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
                    <MenuItem value={new Date().getFullYear() - i}>
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
                  return <MenuItem value={i}>{i + 1}</MenuItem>;
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
                  return <MenuItem value={i + 1}>{i + 1}</MenuItem>;
                })}
              </Select>
            </FormControl>
            <span>日</span>
          </div>
        </div>

        {/* 名称 */}
        <div className="input-name-box">
          <span className="input-span">名称</span>
          <TextField
            id="standard-basic"
            variant="standard"
            fullWidth={true}
            inputProps={{
              style: {
                paddingRight: "20px",
                paddingLeft: "20px",
                fontSize: "20px",
                color: "#424242",
              },
            }}
            value={saving && saving.savingName}
            onChange={changeSavingName}
          />
        </div>

        {/* 貯金額 */}
        <div className="amount-group">
          <span className="input-span">金額</span>
          <div className="saving-amount">
            <TextField
              id="standard-basic"
              variant="standard"
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
                  ? 0
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
            <Select value={distribution} onChange={changeDistribution}>
              {distributionList.map((distributionItem, index) => {
                return (
                  <MenuItem value={distributionItem} key={index}>
                    {distributionItem}
                  </MenuItem>
                );
              })}
            </Select>
          </FormControl>
        </div>

        {/* 登録ボタン */}
        <Button onClick={closeAddSavingWindow} variant="contained">
          登録
        </Button>
      </div>
    </>
  );
};

export default AddSavingBox;
