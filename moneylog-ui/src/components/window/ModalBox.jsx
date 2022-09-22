import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/ModalBox.css";
import "../components_CSS/window_CSS/CategoryWindow.css";
/** 自作コンポーネント */
import CategoryWindow from "./CategoryWindow";
import SwitchBalanceButton from "../SwitchBalanceButton";
/** 外部コンポーネント */
import {
  Button,
  FormControl,
  Select,
  MenuItem,
  FormGroup,
  FormControlLabel,
  Checkbox,
  TextField,
  CircularProgress,
  Autocomplete,
} from "@mui/material";
import { CSSTransition } from "react-transition-group";
import CloseIcon from "@mui/icons-material/Close";
import { useEffect } from "react";

const ModalBox = (props) => {
  const [isLoading, setLoading] = useState(false);
  const {
    title,
    transaction,
    setTransaction,
    openWindow,
    getInit,
    month,
    setBanner,
    setBannerMessage,
    setBannerType,
  } = props;

  useEffect(() => {
    if (transaction.transactionId == void 0) {
      setTransaction({ ...transaction, transactionSign: -1 });
    }
  }, [setTransaction]);

  useEffect(() => {
    /** 日付初期値 */
    if (transaction.transactionDate == void 0) {
      setTransaction({ ...transaction, transactionDate: new Date() });
    }
  }, [setTransaction]);

  /** ウィンドウを閉じる */
  const closeModalWindow = () => {
    setTransaction({});
    openWindow(false);
  };

  /** 金額入力 */
  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    if (!isNaN(tempNum)) {
      setTransaction({
        ...transaction,
        transactionAmount: tempNum,
      });
    }
  };

  const [CategoryWindowModal, setCategoryWindowModal] = useState(false);
  const openCategoryWindow = () => {
    setCategoryWindowModal(true);
  };

  /** 取引名入力 */
  const changeTransactionName = (e) => {
    setTransaction({ ...transaction, transactionName: e.target.value });
  };
  const selectTransactionName = (e, v) => {
    setTransaction({ ...transaction, transactionName: v });
  };

  /** 日付処理 */
  const setYear = (e) => {
    let d = new Date(transaction.transactionDate);
    d.setFullYear(e.target.value);
    setTransaction({ ...transaction, transactionDate: new Date(d) });
  };
  const setMonth = (e) => {
    let d = new Date(transaction.transactionDate);
    d.setMonth(e.target.value);
    setTransaction({ ...transaction, transactionDate: new Date(d) });
  };
  const setDay = (e) => {
    let d = new Date(transaction.transactionDate);
    d.setDate(e.target.value);
    setTransaction({ ...transaction, transactionDate: new Date(d) });
  };

  /** 固定費フラグのセット */
  const [fixedFlg, setFixedFlg] = useState(
    transaction.fixedFlg == void 0 ? false : transaction.fixedFlg
  );
  const changeFixedFlg = (e) => {
    setFixedFlg(e.target.checked);
  };

  /** 取引名のレコメンドリスト */
  const [recommendList, setRecommendList] = useState([]);

  /** API関連 */
  const rootURI = "http://localhost:8080";

  /** 登録ボタン押下処理 */
  const registerTransaction = () => {
    if (transaction.transactionId == void 0) {
      // 登録処理
      addTransaction();
    } else {
      // 編集処理
      editTransaction();
    }
    setBanner(true);
  };

  /** 登録処理 */
  const addTransaction = () => {
    setLoading(true);
    fetch(`${rootURI}/transaction/addTransaction`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        transactionDate: transaction.transactionDate,
        transactionAmount: transaction.transactionAmount,
        transactionSign: transaction.transactionSign,
        transactionName: transaction.transactionName,
        categoryId: transaction.categoryId,
        subCategoryId: transaction.subCategoryId,
        subCategoryName: transaction.subCategoryName,
        fixedFlg: fixedFlg,
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
        closeModalWindow();
        getInit(month);
      });
  };

  /** 編集処理 */
  const editTransaction = () => {
    setLoading(true);
    fetch(`${rootURI}/transaction/editTransaction`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        transactionId: transaction.transactionId,
        transactionDate: transaction.transactionDate,
        transactionAmount: transaction.transactionAmount,
        transactionSign: transaction.transactionSign,
        transactionName: transaction.transactionName,
        categoryId: transaction.categoryId,
        subCategoryId: transaction.subCategoryId,
        subCategoryName: transaction.subCategoryName,
        fixedFlg: fixedFlg,
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
        closeModalWindow();
        getInit(month);
      });
  };

  /** 削除処理 */
  const deleteTransaction = () => {
    setLoading(true);
    fetch(`${rootURI}/transaction/deleteTransaction`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        transactionId: transaction.transactionId,
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
        closeModalWindow();
        getInit(month);
        setBanner(true);
      });
  };

  /** 取引名レコメンド取得 */
  const getFrequentTransactionName = () => {
    fetch(`${rootURI}/transaction/getFrequentTransactionName`, {
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
          setRecommendList(
            data.transactionList.map((data) => data.transactionName)
          );
        } else {
          // 失敗
        }
      });
  };

  useEffect(() => {
    getFrequentTransactionName();
  }, [setRecommendList]);

  return (
    <>
      <div className="modal-window">
        <CloseIcon
          onClick={closeModalWindow}
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
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={new Date(transaction.transactionDate).getFullYear()}
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
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={new Date(transaction.transactionDate).getMonth()}
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
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={new Date(transaction.transactionDate).getDate()}
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
        {/* 金額入力 */}
        <div className="input-amount-box">
          <SwitchBalanceButton
            balance={
              isNaN(transaction.transactionSign)
                ? 0
                : transaction.transactionSign
            }
            transaction={transaction}
            setTransaction={setTransaction}
          />
          <div className="amount-group">
            <span className="input-span">金額</span>
            <div className="input-amount">
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
                  transaction.transactionAmount == void 0
                    ? ""
                    : Math.abs(transaction.transactionAmount).toLocaleString()
                }
                onChange={changeAmount}
              />
              <span className="input-span">円</span>
            </div>
          </div>
        </div>
        {/* 取引名入力 */}
        <div className="input-transaction-box">
          <span className="input-span">取引名</span>
          <div className="input-transaction">
            <Autocomplete
              freeSolo
              options={recommendList}
              defaultValue={transaction && transaction.transactionName}
              onChange={selectTransactionName}
              renderInput={(params) => (
                <TextField
                  {...params}
                  variant="standard"
                  fullWidth={true}
                  autoComplete="off"
                  onChange={changeTransactionName}
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
        </div>
        {/* カテゴリ入力 */}
        <div className="input-category-box">
          <span className="input-span category-span">カテゴリ</span>
          <div onClick={openCategoryWindow} className="category-box">
            <p>
              <span style={{ fontSize: "14px" }}>
                {!transaction.subCategoryName
                  ? ""
                  : transaction.categoryName +
                    " / " +
                    transaction.subCategoryName}
              </span>
              <span>&gt;</span>
            </p>
          </div>
        </div>
        {/* 固定費チェックボックス */}
        <FormGroup>
          <FormControlLabel
            className={"fixed-check-box"}
            control={<Checkbox color="default" />}
            label="固定費として計算する"
            onChange={changeFixedFlg}
            checked={fixedFlg}
          />
        </FormGroup>

        {/* 登録ボタン */}
        <Button
          onClick={registerTransaction}
          variant="contained"
          disabled={isLoading}
        >
          {isLoading ? <CircularProgress size={20} /> : "登録"}
        </Button>

        {/* 削除ボタン */}
        {transaction.transactionId && (
          <div className="deleteButton">
            <Button
              onClick={deleteTransaction}
              disabled={isLoading}
              size="small"
              sx={{ color: "#9e9e9e" }}
            >
              削除
            </Button>
          </div>
        )}
      </div>

      <CSSTransition
        in={CategoryWindowModal}
        timeout={300}
        unmountOnExit
        classNames="Category-show"
      >
        <CategoryWindow
          setCategoryWindowModal={setCategoryWindowModal}
          closeModalWindow={closeModalWindow}
          // 以下必須
          setTransaction={setTransaction}
          transaction={transaction}
        />
      </CSSTransition>
    </>
  );
};

export default ModalBox;
