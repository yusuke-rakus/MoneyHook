import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/AddTargetBox.css";
/** 外部コンポーネント */
import CloseIcon from "@mui/icons-material/Close";
import { TextField, Button, CircularProgress } from "@mui/material";
import { useCookies } from "react-cookie";

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
  const [cookie, setCookie] = useCookies();
  const [label, setLabel] = useState({
    savingTargetName: { message: "", status: false },
    targetAmount: { message: "", status: false },
  });

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
    setBanner(false);

    // バリデーションチェック
    // 未入力チェック
    if (!savingTargetData.savingTargetName || !savingTargetData.targetAmount) {
      setLabel({
        ...label,
        savingTargetName: {
          message: "未入力",
          status: !savingTargetData.savingTargetName,
        },
        targetAmount: {
          message: "未入力",
          status: !savingTargetData.targetAmount,
        },
      });
      return;
    }

    if (savingTargetData.savingTargetId == void 0) {
      // 登録処理
      addSavingTarget();
    } else {
      // 編集処理
      editSavingTarget();
    }
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
        userId: cookie.userId,
        savingTargetName: savingTargetData.savingTargetName,
        targetAmount: savingTargetData.targetAmount,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功
          setBanner(true);
          setBannerMessage(data.message);
          setBannerType(data.status);
          closeAddTargetStatus();
          getInit();
        } else {
          // 失敗
          setLabel((l) => ({
            ...l,
            savingTargetName: {
              message: "同名の貯金目標が存在します",
              status: true,
            },
            targetAmount: {
              message: "",
              status: false,
            },
          }));
        }
      })
      .finally(() => {
        setLoading(false);
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
        userId: cookie.userId,
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
        closeAddTargetStatus();
        setBanner(true);
        getInit();
      });
  };

  /** 貯金削除 */
  const deleteSavingTarget = () => {
    setBanner(false);
    setLoading(true);
    fetch(`${rootURI}/savingTarget/deleteSavingTarget`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        savingTargetId: savingTargetData.savingTargetId,
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
        closeAddTargetStatus();
        setBanner(true);
        getInit();
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
          error={label.savingTargetName.status}
          label={label.savingTargetName.message}
          fullWidth={true}
          value={
            savingTargetData.savingTargetName &&
            savingTargetData.savingTargetName
          }
          onChange={changeName}
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
            error={label.targetAmount.status}
            label={label.targetAmount.message}
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

      {/* 削除ボタン */}
      {savingTargetData.savingTargetId && (
        <div className="deleteButton">
          <Button
            onClick={deleteSavingTarget}
            disabled={isLoading}
            size="small"
            sx={{ color: "#9e9e9e" }}
          >
            削除
          </Button>
        </div>
      )}
    </div>
  );
};
export default AddTargetBox;
