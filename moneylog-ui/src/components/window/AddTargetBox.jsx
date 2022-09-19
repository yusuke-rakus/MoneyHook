import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/AddTargetBox.css";
/** 外部コンポーネント */
import CloseIcon from "@mui/icons-material/Close";
import { TextField, Button, CircularProgress } from "@mui/material";

const AddTargetBox = (props) => {
  const {
    setWindowStatus,
    savingTargetData,
    setSavingTargetData,
    title,
    getInit,
    setBanner,
    setBannerMessage,
    setBannerType,
  } = props;

  const [isLoading, setLoading] = useState(false);

  /** モーダルウィンドウを閉じる */
  const closeAddTargetStatus = () => {
    setWindowStatus(false);
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

  /** API関連 */
  const rootURI = "http://localhost:8080";

  /** 登録処理 */
  const register = () => {
    if (savingTargetData.savingTargetId == void 0) {
      // 登録処理
      addSavingTarget();
    } else {
      // 編集処理
      editSavingTarget();
    }
    setBanner(true);
  };

  /** 貯金目標を追加 */
  const addSavingTarget = () => {
    setLoading(true);
    fetch(`${rootURI}/savingTarget/addSavingTarget`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        savingTargetName: savingTargetData.savingTargetName,
        targetAmount: savingTargetData.targetAmount,
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
        getInit();
        closeAddTargetStatus();
      });
  };

  /** 貯金目標を編集 */
  const editSavingTarget = () => {
    setLoading(true);
    fetch(`${rootURI}/savingTarget/editSavingTarget`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        savingTargetId: savingTargetData.savingTargetId,
        savingTargetName: savingTargetData.savingTargetName,
        targetAmount: savingTargetData.targetAmount,
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
        getInit();
        closeAddTargetStatus();
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
                ? ""
                : Math.abs(savingTargetData.targetAmount).toLocaleString()
            }
            onChange={changeAmount}
          />
          <span className="input-span">円</span>
        </div>
      </div>

      {/* 登録ボタン */}
      <Button onClick={register} variant="contained" disabled={isLoading}>
        {isLoading ? <CircularProgress size={20} /> : "登録"}
      </Button>
    </div>
  );
};
export default AddTargetBox;
