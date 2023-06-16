import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/AddTargetBox.css";
/** 自作コンポーネント */
import { rootURI } from "../../env/env";
import { PostErrorWithSeparateBanner } from "../FetchError";
import { useCookies } from "react-cookie";
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
  const [cookie] = useCookies();
  const [label, setLabel] = useState({
    savingTargetName: { message: "名称", status: false },
    targetAmount: { message: "金額", status: false },
  });

  /** モーダルウィンドウを閉じる */
  const closeAddTargetStatus = () => {
    setWindowStatus(false);
  };

  /** 金額入力 */
  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    if (tempNum > 99999999) {
      setLabel({
        ...label,
        targetAmount: {
          message: "¥99,999,999以内",
          status: true,
        },
      });
      return;
    } else {
      setLabel({
        ...label,
        targetAmount: {
          message: "金額",
          status: false,
        },
      });
    }
    setSavingTargetData({ ...savingTargetData, targetAmount: tempNum });
  };

  /** 目標名称入力 */
  const changeName = (e) => {
    if (e.target.value.length > 32) {
      setLabel({
        ...label,
        savingTargetName: {
          message: "32文字以内",
          status: true,
        },
      });
      return;
    } else {
      setLabel({
        ...label,
        savingTargetName: {
          message: "名称",
          status: false,
        },
      });
    }
    setSavingTargetData({
      ...savingTargetData,
      savingTargetName: e.target.value,
    });
  };

  /** キーボードで登録ボタン押下 */
  const onEnterTargetAmount = (e) => {
    if (e.keyCode === 13) {
      register();
    }
  };

  /** API関連 */
  /** 登録処理 */
  const register = () => {
    setBanner(false);

    // バリデーションチェック
    if (!savingTargetData.savingTargetName || !savingTargetData.targetAmount) {
      setLabel({
        ...label,
        savingTargetName: {
          message: !savingTargetData.savingTargetName
            ? "未入力"
            : label.savingTargetName.message,
          status: !savingTargetData.savingTargetName
            ? true
            : label.savingTargetName.status,
        },
        targetAmount: {
          message: !savingTargetData.targetAmount
            ? "未入力"
            : label.targetAmount.message,
          status: !savingTargetData.targetAmount
            ? true
            : label.targetAmount.status,
        },
      });
      return;
    }

    if (savingTargetData.savingTargetId === void 0) {
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
        if (data.status === "success") {
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
      })
      .catch(() =>
        PostErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType,
          closeAddTargetStatus
        )
      );
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
        closeAddTargetStatus();
        setBanner(true);
        getInit();
      })
      .catch(() =>
        PostErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType,
          closeAddTargetStatus
        )
      );
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
        closeAddTargetStatus();
        setBanner(true);
        getInit();
      })
      .catch(() =>
        PostErrorWithSeparateBanner(
          setLoading,
          setBanner,
          setBannerMessage,
          setBannerType,
          closeAddTargetStatus
        )
      );
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
        <span
          className="input-span"
          style={label.savingTargetName.status ? { color: "#c62828" } : {}}
        >
          {label.savingTargetName.message}
        </span>
        <TextField
          id="standard-basic"
          variant="standard"
          autoComplete="off"
          error={label.savingTargetName.status}
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
        <span
          className="input-span"
          style={label.targetAmount.status ? { color: "#c62828" } : {}}
        >
          {label.targetAmount.message}
        </span>
        <div className="target-amount">
          <TextField
            id="standard-basic"
            variant="standard"
            autoComplete="off"
            error={label.targetAmount.status}
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
            onKeyUp={onEnterTargetAmount}
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
