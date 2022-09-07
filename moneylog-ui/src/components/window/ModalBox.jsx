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
} from "@mui/material";
import { CSSTransition } from "react-transition-group";
import CloseIcon from "@mui/icons-material/Close";

const ModalBox = (props) => {
  const { transaction, setTransaction, openWindow } = props;

  /** 貯金日がなければ当日をセット */
  if (!transaction.transactionDate) {
    setTransaction({ ...transaction, transactionDate: new Date() });
  }

  /** ウィンドウを閉じる */
  const closeModalWindow = () => {
    setTransaction({});
    openWindow(false);
  };

  /** 登録ボタン押下 */
  const registerTransaction = () => {
    closeModalWindow();
  };

  /** 金額入力 */
  const changeAmount = (e) => {
    let tempNum = String(e.target.value).replace(/,/g, "");
    setTransaction({
      ...transaction,
      transactionAmount: tempNum,
    });
  };

  const [CategoryWindowModal, setCategoryWindowModal] = useState(false);
  const openCategoryWindow = () => {
    setCategoryWindowModal(true);
  };

  /** 取引名入力 */
  const changeTransactionName = (e) => {
    setTransaction({ ...transaction, transactionName: e.target.value });
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

  return (
    <>
      <div className="modal-window">
        <CloseIcon
          onClick={closeModalWindow}
          style={{ cursor: "pointer", color: "#a9a9a9" }}
          className="close-button"
        />
        <h3 className="modal-title">支出または収入の入力</h3>

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
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={new Date(transaction.transactionDate).getMonth()}
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
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={new Date(transaction.transactionDate).getDate()}
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

        {/* 金額入力 */}
        <div className="input-amount-box">
          <SwitchBalanceButton
            balance={
              isNaN(transaction.transactionAmount)
                ? 0
                : transaction.transactionAmount
            }
          />
          <div className="amount-group">
            <span className="input-span">金額</span>
            <div className="input-amount">
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
                  isNaN(transaction.transactionAmount)
                    ? 0
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
            <TextField
              id="standard-basic"
              variant="standard"
              autoComplete="off"
              fullWidth={true}
              inputProps={{
                style: {
                  paddingRight: "20px",
                  paddingLeft: "20px",
                  fontSize: "20px",
                  color: "#424242",
                },
              }}
              value={transaction && transaction.transactionName}
              onChange={changeTransactionName}
            />
          </div>
        </div>

        {/* カテゴリ入力 */}
        <div className="input-category-box">
          <span className="input-span category-span">カテゴリ</span>
          <div onClick={openCategoryWindow} className="category-box">
            <span>
              {!transaction.subCategoryName
                ? ""
                : transaction.categoryName +
                  " / " +
                  transaction.subCategoryName}
            </span>
            <span>&gt;</span>
          </div>
        </div>

        {/* 固定費チェックボックス */}
        <FormGroup>
          <FormControlLabel
            className={"fixed-check-box"}
            control={<Checkbox color="default" />}
            label="固定費として計算する"
          />
        </FormGroup>

        {/* 登録ボタン */}
        <Button onClick={registerTransaction} variant="contained">
          登録
        </Button>
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
