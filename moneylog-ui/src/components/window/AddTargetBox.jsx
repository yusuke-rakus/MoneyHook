import React, { useState } from "react";
import CloseIcon from "@mui/icons-material/Close";
import { TextField, Button } from "@mui/material";
import "./components_CSS/AddTargetBox.css";

const AddTargetBox = (props) => {
  const { setAddTargetStatus } = props;
  const [targetAmount, setTargetAmount] = useState("");

  const closeAddTargetStatus = () => {
    setAddTargetStatus(false);
  };

  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    if (isNaN(tempNum)) {
      return;
    }
    setTargetAmount(Number(tempNum).toLocaleString());
  };

  return (
    <>
      <div className="mini-modal-window">
        <CloseIcon
          onClick={closeAddTargetStatus}
          style={{ cursor: "pointer", color: "#a9a9a9" }}
          className="close-button"
        />
        <h3 className="modal-title">目標を追加</h3>

        {/* 目標名称 */}
        <div className="input-name-box">
          <span className="input-span">名称</span>
          <TextField id="standard-basic" variant="standard" fullWidth={true} />
        </div>

        {/* 目標金額 */}
        <div className="amount-group">
          <span className="input-span">金額</span>
          <div className="target-amount">
            <TextField
              id="standard-basic"
              variant="standard"
              fullWidth={true}
              inputProps={{
                style: { textAlign: "right", paddingRight: "5px" },
              }}
              value={targetAmount}
              onChange={changeAmount}
            />
            <span className="input-span">円</span>
          </div>
        </div>

        {/* 登録ボタン */}
        <Button onClick={closeAddTargetStatus} variant="contained">
          登録
        </Button>
      </div>
    </>
  );
};
export default AddTargetBox;
