import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/UncategorizedSavingWindow.css";
/** 外部コンポーネント */
import CloseIcon from "@mui/icons-material/Close";
import {
  Button,
  Card,
  CardContent,
  Checkbox,
  CircularProgress,
  FormControl,
  FormControlLabel,
  FormGroup,
  InputLabel,
  MenuItem,
  Select,
} from "@mui/material";
import { useEffect } from "react";
import { useCookies } from "react-cookie";

const UncategorizedSavingWindow = (props) => {
  const { setUncategorizedWindow, uncategorizedAmount } = props;
  const [cookie, setCookie] = useCookies();

  /** 変数 */
  const [isLoading, setLoading] = useState(false);
  const [checked, setChecked] = useState(false);
  // 貯金目標リスト
  const [savingTargetList, setSavingTargetList] = useState([]);
  // 未分類の貯金リスト
  const [savingList, setSavingList] = useState([]);
  // API送信用貯金リスト
  const [savingIdList, setSavingIdList] = useState([]);
  // API送信用目標ID
  const [selectedTargetId, setSelectedTargetId] = useState("");

  /** 操作 */
  /** モーダルを閉じる */
  const closeModalWindow = () => {
    setUncategorizedWindow(false);
  };

  /** すべて選択 */
  const checkAll = (e) => {
    setChecked(e.target.checked);
    e.target.checked
      ? setSavingIdList(savingList.map((data) => data.savingId))
      : setSavingIdList([]);
  };

  /** 貯金選択 */
  const checkSaving = (e) => {
    e.target.checked
      ? setSavingIdList([...savingIdList, Number(e.target.value)])
      : setSavingIdList(
          savingIdList.filter((data) => data !== Number(e.target.value))
        );
  };

  /** 目標選択 */
  const selectSavingTarget = (e) => {
    setSelectedTargetId(e.target.value);
  };

  /** API関連 */

  const rootURI = "http://localhost:8080";

  const getInit = () => {
    getSavingList();
    getSavingTargetList();
  };

  const getSavingList = () => {
    fetch(`${rootURI}/saving/getUncategorizedSaving`, {
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
        if (data.status == "success") {
          // 成功
          setSavingList(data.savingList);
        } else {
          // 失敗
        }
      });
  };

  const getSavingTargetList = () => {
    setLoading(true);
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
        if (data.status == "success") {
          // 成功
          setSavingTargetList(data.savingTarget);
        } else {
          // 失敗
        }
      })
      .finally(() => {
        setLoading(false);
      });
  };

  /** 登録処理 */
  const sortSavingAmount = () => {
    console.log(`貯金目標ID: ${selectedTargetId}`);
    console.log(`貯金IDリスト: ${savingIdList}`);
  };

  useEffect(() => {
    getInit();
  }, [setSavingList, setSavingTargetList]);

  return (
    <div className="UncategorizedSavingWindow">
      {/* 閉じるボタン */}
      <CloseIcon
        onClick={closeModalWindow}
        style={{ cursor: "pointer", color: "#a9a9a9" }}
        className="close-button"
      />

      {/* タイトル */}
      <h3 className="modal-title">貯金を振り分ける</h3>

      {/* 貯金額 */}
      <h3 className="uncategorizedAmount">
        未分類の貯金額：
        <span
          style={{ color: "#1B5E20" }}
        >{`¥${uncategorizedAmount.toLocaleString()}`}</span>
      </h3>

      {/* 未分類の貯金リスト */}
      <div className="uncategorizedSavingList">
        <Card variant="outlined" sx={{ height: "100%" }}>
          <CardContent
            variant="outlined"
            sx={{ borderBottom: 1, borderColor: "rgba(0, 0, 0, 0.12)" }}
          >
            <div className="formGroup">
              {/* すべて選択 */}
              <FormGroup>
                <FormControlLabel
                  control={
                    <Checkbox
                      checked={
                        checked ||
                        (savingIdList.length === savingList.length &&
                          savingList.length !== 0)
                      }
                    />
                  }
                  label="すべて選択"
                  sx={{ userSelect: "none" }}
                  onChange={checkAll}
                />
              </FormGroup>

              {/* 振り分け先 */}
              <FormControl sx={{ minWidth: 120 }} size="small">
                <InputLabel sx={{ fontSize: 13 }} id="demo-select-small">
                  振り分け先
                </InputLabel>
                <Select
                  labelId="demo-select-small"
                  id="demo-select-small"
                  label="振り分け先"
                  value={selectedTargetId}
                  onChange={selectSavingTarget}
                >
                  {savingTargetList.map((data, i) => {
                    return (
                      <MenuItem value={data.savingTargetId} key={i}>
                        {data.savingTargetName}
                      </MenuItem>
                    );
                  })}
                </Select>
              </FormControl>
            </div>
          </CardContent>

          {/* 貯金一覧 */}
          <CardContent sx={{ paddingX: 5, height: "80%", overflowY: "auto" }}>
            {isLoading ? (
              <CircularProgress />
            ) : (
              <ul>
                {savingList.map((data, i) => {
                  return (
                    <li className="uncategorizedSavingData" key={i}>
                      <FormGroup>
                        <FormControlLabel
                          control={
                            <Checkbox
                              checked={
                                checked || savingIdList.includes(data.savingId)
                              }
                            />
                          }
                          label={data.savingName}
                          sx={{ userSelect: "none" }}
                          value={data.savingId}
                          onChange={checkSaving}
                        />
                      </FormGroup>
                      <span>{`¥${data.savingAmount.toLocaleString()}`}</span>
                    </li>
                  );
                })}
              </ul>
            )}
          </CardContent>
        </Card>
      </div>

      {/* 登録ボタン */}
      <Button
        onClick={sortSavingAmount}
        variant="contained"
        disabled={isLoading}
      >
        {isLoading ? <CircularProgress size={20} /> : "登録"}
      </Button>
    </div>
  );
};

export default UncategorizedSavingWindow;
