import React from "react";
/** CSS */
import "./components_CSS/SettingsFixed.css";
/** 自作コンポーネント */
import SwitchBalanceButton from "../components/SwitchBalanceButton";
/** 外部コンポーネント */
import {
  TextField,
  Button,
  FormControl,
  IconButton,
  NativeSelect,
  CircularProgress,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

const SettingsFixed = (props) => {
  const {
    data,
    monthlyTransactionList,
    setMonthlyTransactionList,
    getInit,
    isLoading,
    setLoading,
  } = props;

  /** 取引名の変更 */
  const transactionChange = (e) => {
    data.monthlyTransactionName = e.target.value;
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt) =>
        mt.monthlyTransactionId === data.monthlyTransactionId ? data : mt
      )
    );
  };

  /** 日付の変更 */
  const dateChange = (e) => {
    data.monthlyTransactionDate = e.target.value;
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt) =>
        mt.monthlyTransactionId === data.monthlyTransactionId ? data : mt
      )
    );
  };

  /** 金額の変更 */
  const amountChange = (e) => {
    data.monthlyTransactionAmount = String(e.target.value).replace(/,/g, "");
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt) =>
        mt.monthlyTransactionId === data.monthlyTransactionId ? data : mt
      )
    );
  };

  /** API関連 */
  const rootURI = "http://localhost:8080";

  /** 削除 */
  const deleteData = () => {
    setLoading(true);
    fetch(`${rootURI}/fixed/deleteFixed`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        monthlyTransactionId: data.monthlyTransactionId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功
        } else {
          // 失敗
        }
      })
      .finally(() => {
        setLoading(false);
        getInit();
      });
  };

  return (
    <div className="fixedData">
      {/* カテゴリ */}
      <div className="categoryData">
        <Button
          variant="text"
          sx={{
            color: "#424242",
            transition: "0.25s",
            "&:hover": {
              background: "#eeeeee",
            },
          }}
        >
          {data.categoryName !== null
            ? `${data.categoryName}/${data.subCategoryName}`
            : "カテゴリ選択"}
        </Button>
      </div>

      {/* 取引名 */}
      <div className="transactionNameData">
        <span>取引名</span>
        <TextField
          variant="standard"
          autoComplete="off"
          value={data.monthlyTransactionName}
          onChange={transactionChange}
        />
      </div>

      {/* 金額 */}
      <div className="fixedAmountData">
        <span>金額</span>
        <TextField
          variant="standard"
          autoComplete="off"
          inputProps={{
            style: {
              textAlign: "right",
              paddingRight: "20px",
              paddingLeft: "20px",
            },
          }}
          value={
            isNaN(data.monthlyTransactionAmount)
              ? 0
              : Math.abs(data.monthlyTransactionAmount).toLocaleString()
          }
          onChange={amountChange}
        />
      </div>

      {/* 振替日 */}
      <div className="transferDate">
        <span>振替日</span>
        <FormControl sx={{ minWidth: 60 }} size="small">
          <NativeSelect
            value={
              data.monthlyTransactionDate
                ? data.monthlyTransactionDate
                : (data.monthlyTransactionDate = 31)
            }
            onChange={dateChange}
            inputProps={{ name: "age", id: "uncontrolled-native" }}
          >
            {[...Array(30)].map((v, i) => {
              return (
                <option key={i} value={i + 1}>
                  {i + 1}
                </option>
              );
            })}
            <option value="31">末</option>
          </NativeSelect>
        </FormControl>
        日
      </div>

      {/* 収支ボタン */}
      <div className="switchBalanceArea">
        <SwitchBalanceButton
          balance={data.monthlyTransactionAmount}
          id={data.monthlyTransactionId}
        />
      </div>
      <div className="deleteArea">
        {isLoading ? (
          <CircularProgress size={20} />
        ) : (
          <IconButton onClick={deleteData} aria-label="delete">
            <DeleteIcon />
          </IconButton>
        )}
      </div>
    </div>
  );
};

export default SettingsFixed;
