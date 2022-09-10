import React from "react";
/** CSS */
import "../components_CSS/window_CSS/AddTargetBox.css";
/** 外部コンポーネント */
import CloseIcon from "@mui/icons-material/Close";
import { TextField, Button } from "@mui/material";

const AddTargetBox = (props) => {
  const { setWindowStatus, savingTargetData, setSavingTargetData, title } =
    props;

  /** モーダルウィンドウを閉じる */
  const closeAddTargetStatus = () => {
    setWindowStatus(false);
  };

  /** 登録ボタン押下 */
  const register = () => {
    console.log(savingTargetData);
    closeAddTargetStatus();
  };

  /** 金額入力 */
  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    setSavingTargetData({ ...savingTargetData, targetAmount: tempNum });
  };

  /** 目標名称入力 */
  const changeName = (e) => {
    setSavingTargetData({
      ...savingTargetData,
      savingTargetName: e.target.value,
    });
  };

  return (
    <div className="mini-modal-window">
      <CloseIcon
        onClick={closeAddTargetStatus}
        style={{ cursor: "pointer", color: "#a9a9a9" }}
        className="close-button"
      />
      <h3 className="modal-title">{title}</h3>

      {/* 目標名称 */}
      <div className="input-name-box">
        <span className="input-span">名称</span>
        <TextField
          id="standard-basic"
          variant="standard"
          autoComplete="off"
          value={
            savingTargetData.savingTargetName &&
            savingTargetData.savingTargetName
          }
          onChange={changeName}
          fullWidth={true}
          inputProps={{
            style: {
              paddingRight: "20px",
              paddingLeft: "20px",
            },
          }}
        />
      </div>

      {/* 目標金額 */}
      <div className="amount-group">
        <span className="input-span">金額</span>
        <div className="target-amount">
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
              },
            }}
            value={
              isNaN(savingTargetData.targetAmount)
                ? 0
                : Math.abs(savingTargetData.targetAmount).toLocaleString()
            }
            onChange={changeAmount}
          />
          <span className="input-span">円</span>
        </div>
      </div>

      {/* 登録ボタン */}
      <Button onClick={register} variant="contained">
        登録
      </Button>
    </div>
  );
};
export default AddTargetBox;
