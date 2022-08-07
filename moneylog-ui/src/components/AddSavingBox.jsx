import React, { useState } from "react";
import "./components_CSS/AddSavingBox.css";
import CloseIcon from "@mui/icons-material/Close";
import {
  FormControl,
  Select,
  MenuItem,
  TextField,
  Button,
} from "@mui/material";

const AddSavingBox = (props) => {
  const { setAddSavingStatus } = props;

  // 日付処理
  const [date, setDate] = useState({ year: "", month: "", day: "" });
  const setYear = (e) => setDate({ ...date, year: e.target.value });
  const setMonth = (e) => setDate({ ...date, month: e.target.value });
  const setDay = (e) => setDate({ ...date, day: e.target.value });

  // 振り分け処理
  const [distribution, setDistribution] = useState("");
  const distributionList = ["M2 MacBookAir", "沖縄旅行"];
  const changeDistribution = (e) => {
    setDistribution(e.target.value);
  };

  // 閉じる処理
  const closeAddSavingWindow = () => {
    setAddSavingStatus(false);
  };

  // 金額表示処理
  const [amount, setAmount] = useState("");
  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    if (isNaN(tempNum)) {
      return;
    }
    setAmount(Number(tempNum).toLocaleString());
  };

  return (
    <>
      <div className="modal-window">
        <CloseIcon
          onClick={closeAddSavingWindow}
          style={{ cursor: "pointer", color: "#a9a9a9" }}
          className="close-button"
        />
        <h3 className="modal-title">貯金を追加</h3>

        {/* 日付入力 */}
        <div className="input-date-box">
          <span className="input-span">日付</span>
          <div className="date-group">
            <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
              <Select
                value={date.year}
                onChange={setYear}
                defaultValue={date.year}
              >
                <MenuItem value={2020}>2020</MenuItem>
                <MenuItem value={2021}>2021</MenuItem>
              </Select>
            </FormControl>
            <span>年</span>
            <FormControl sx={{ m: 1, minWidth: 60 }} size="small">
              <Select value={date.month} onChange={setMonth}>
                <MenuItem value={6}>6</MenuItem>
                <MenuItem value={7}>7</MenuItem>
              </Select>
            </FormControl>
            <span>月</span>
            <FormControl sx={{ m: 1, minWidth: 60 }} size="small">
              <Select value={date.day} onChange={setDay}>
                <MenuItem value={25}>25</MenuItem>
                <MenuItem value={26}>26</MenuItem>
              </Select>
            </FormControl>
            <span>日</span>
          </div>
        </div>

        {/* 名称 */}
        <div className="input-name-box">
          <span className="input-span">名称</span>
          <TextField id="standard-basic" variant="standard" fullWidth={true} />
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
                style: { textAlign: "right", paddingRight: "5px" },
              }}
              value={amount}
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
