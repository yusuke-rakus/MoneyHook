import React, { useState } from "react";
import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
/** CSS */
import "./components_CSS/SettingsFixed.css";
/** 自作コンポーネント */
import SwitchBalanceButton from "../components/SwitchBalanceButton";
import { rootURI } from "../env/env";
import { SettingsFetchError } from "./FetchError";
/** 外部コンポーネント */
import {
  TextField,
  FormControl,
  IconButton,
  NativeSelect,
  CircularProgress,
  MenuItem,
  Select,
  InputLabel,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

const SettingsFixed = (props) => {
  const {
    data,
    index,
    monthlyTransactionList,
    setMonthlyTransactionList,
    getInit,
    isLoading,
    setLoading,
    banner,
    setBanner,
  } = props;
  const [cookie] = useCookies();
  const navigate = useNavigate();

  /** 取引名の変更 */
  const transactionChange = (e) => {
    data.monthlyTransactionName = e.target.value;
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt, i) => (i === index ? data : mt))
    );
  };

  /** 日付の変更 */
  const dateChange = (e) => {
    data.monthlyTransactionDate = e.target.value;
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt, i) => (i === index ? data : mt))
    );
  };

  /** 金額の変更 */
  const amountChange = (e) => {
    data.monthlyTransactionAmount = String(e.target.value).replace(/,/g, "");
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt, i) => (i === index ? data : mt))
    );
  };

  /** 符号の変更 */
  const changeSign = () => {
    if (data.monthlyTransactionSign === -1) {
      setMonthlyTransactionList(
        monthlyTransactionList.map((mt, i) =>
          i === index ? { ...mt, monthlyTransactionSign: 1 } : mt
        )
      );
    } else {
      setMonthlyTransactionList(
        monthlyTransactionList.map((mt, i) =>
          i === index ? { ...mt, monthlyTransactionSign: -1 } : mt
        )
      );
    }
  };

  /** API関連 */
  const [categoryList, setCategoryList] = useState([]);
  const [subCategoryList, setSubCategoryList] = useState([]);

  /** カテゴリ取得 */
  const getCategory = () => {
    fetch(`${rootURI}/category/getCategoryList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({}),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          setCategoryList(data.categoryList);
          // 成功
        } else {
          // 失敗
        }
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  /** サブカテゴリ取得 */
  const getSubCategory = (categoryId) => {
    fetch(`${rootURI}/subCategory/getSubCategoryList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        categoryId: categoryId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          setSubCategoryList(data.subCategoryList);
          // 成功
        } else {
          // 失敗
        }
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  /** カテゴリを選択 */
  const selectCategory = (e) => {
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt, i) =>
        i === index ? { ...mt, categoryId: e.target.value } : mt
      )
    );
    getSubCategory(e.target.value);
  };

  /** サブカテゴリを選択 */
  const selectSubCategory = (e) => {
    setMonthlyTransactionList(
      monthlyTransactionList.map((mt, i) =>
        i === index ? { ...mt, subCategoryId: e.target.value } : mt
      )
    );
  };

  /** 削除 */
  const deleteData = () => {
    // IDが存在しない場合、リストから除外するのみ
    if (!data.monthlyTransactionId) {
      setMonthlyTransactionList(
        monthlyTransactionList.filter((data, i) => i !== index)
      );
      return;
    }
    setLoading(true);
    fetch(`${rootURI}/fixed/deleteFixed`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        monthlyTransactionId: data.monthlyTransactionId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功
        } else {
          // 失敗
        }
        setBanner({
          ...banner,
          bannerMessage: data.message,
          bannerType: data.status,
          banner: true,
        });
      })
      .finally(() => {
        setLoading(false);
        getInit();
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  useEffect(() => {
    !cookie.userId && navigate("/login");
    getCategory();
    getSubCategory(data.categoryId);
  }, [setCategoryList]);

  return (
    <div className="fixedData">
      <div className="categoryData">
        {/* カテゴリ */}
        <FormControl
          sx={{ m: 1, width: 100 }}
          size="small"
          variant="standard"
          error={data.label.category.status}
        >
          <InputLabel id="demo-select-small" sx={{ fontSize: 13 }}>
            {data.label.category.message}
          </InputLabel>
          <Select
            labelId="demo-select-small"
            id="demo-select-small"
            sx={{ fontSize: 13 }}
            value={data.categoryId}
            onChange={selectCategory}
          >
            {categoryList.map((category) => {
              return (
                <MenuItem value={category.categoryId} key={category.categoryId}>
                  {category.categoryName}
                </MenuItem>
              );
            })}
          </Select>
        </FormControl>

        {/* サブカテゴリ */}
        <FormControl
          sx={{ m: 1, width: 100 }}
          size="small"
          variant="standard"
          error={data.label.subCategory.status}
        >
          <InputLabel id="demo-select-small" sx={{ fontSize: 13 }}>
            {data.label.subCategory.message}
          </InputLabel>
          <Select
            labelId="demo-select-small"
            id="demo-select-small"
            sx={{ fontSize: 13 }}
            value={data.subCategoryId}
            onChange={selectSubCategory}
          >
            {subCategoryList.map((subCategory) => {
              return (
                <MenuItem
                  value={subCategory.subCategoryId}
                  key={subCategory.subCategoryId}
                >
                  {subCategory.subCategoryName}
                </MenuItem>
              );
            })}
          </Select>
        </FormControl>
      </div>

      {/* 取引名 */}
      <div className="transactionNameData">
        <span
          style={
            data.label.monthlyTransactionName.status ? { color: "#c62828" } : {}
          }
        >
          {data.label.monthlyTransactionName.message}
        </span>
        <TextField
          variant="standard"
          autoComplete="off"
          error={data.label.monthlyTransactionName.status}
          value={data.monthlyTransactionName}
          onChange={transactionChange}
          inputProps={{
            style: {
              fontSize: 13,
            },
          }}
        />
      </div>

      {/* 金額 */}
      <div className="fixedAmountData">
        <span
          style={
            data.label.monthlyTransactionAmount.status
              ? { color: "#c62828" }
              : {}
          }
        >
          {data.label.monthlyTransactionAmount.message}
        </span>
        <TextField
          variant="standard"
          autoComplete="off"
          error={data.label.monthlyTransactionAmount.status}
          inputProps={{
            style: {
              fontSize: 14,
              textAlign: "right",
              paddingRight: "20px",
              paddingLeft: "20px",
            },
          }}
          value={
            data.monthlyTransactionAmount === void 0
              ? ""
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
            inputProps={{
              name: "age",
              id: "uncontrolled-native",
            }}
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
          balance={data.monthlyTransactionSign}
          id={index}
          changeSign={changeSign}
        />
      </div>

      {/* 削除ボタン */}
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
