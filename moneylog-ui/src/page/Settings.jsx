import React from "react";
import "./page_CSS/Settings.css";
import "./page_CSS/common.css";
import {
  TextField,
  Button,
  FormControl,
  IconButton,
  NativeSelect,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import SwitchBalanceButton from "../components/SwitchBalanceButton";

const Settings = () => {
  return (
    <div className="container">
      {/* ユーザー設定変更 */}
      <div className="containerBox">
        <p className="settingsTitle">ユーザー設定変更</p>
        <hr className="border" />
        <div className="emailBox">
          <span>メールアドレス</span>
          <TextField id="standardBasic" variant="standard" fullWidth={true} />
        </div>

        <div className="userSettingsButtons">
          <Button variant="contained" color="inherit" sx={"color:#757575"}>
            キャンセル
          </Button>
          <Button variant="contained">登録</Button>
        </div>
      </div>

      {/* パスワード変更 */}
      <div className="containerBox">
        <p className="settingsTitle">パスワード変更</p>
        <hr className="border" />
        <div className="passwordBox">
          <span>現在のパスワード</span>
          <TextField id="standardBasic" variant="standard" fullWidth={true} />
        </div>
        <div className="passwordBox">
          <span>変更後のパスワード</span>
          <TextField id="standardBasic" variant="standard" fullWidth={true} />
        </div>
        <div className="passwordBox">
          <span>再入力</span>
          <TextField id="standardBasic" variant="standard" fullWidth={true} />
        </div>

        <div className="passwordSettingsButtons">
          <Button variant="contained" color="inherit" sx={"color:#757575"}>
            キャンセル
          </Button>
          <Button variant="contained">登録</Button>
        </div>
      </div>

      {/* 固定費の編集 */}
      <div className="containerBox">
        <p className="settingsTitle">固定費の編集</p>
        <hr className="border" />
        <div className="fixedListArea">
          <div className="fixedData">
            <div className="categoryData">
              <Button variant="text" sx={"color:#424242"}>
                住宅/家賃
              </Button>
            </div>
            <div className="transactionNameData">
              <span>取引名</span>
              <TextField id="standardBasic" variant="standard" />
            </div>
            <div className="fixedAmountData">
              <span>金額</span>
              <TextField id="standardBasic" variant="standard" />
            </div>
            <div className="transferDate">
              <span>振替日</span>
              <FormControl sx={{ minWidth: 60 }} size="small">
                <NativeSelect>
                  <option value=""></option>
                  <option value={1}>1</option>
                  <option value={2}>2</option>
                  <option value={3}>3</option>
                </NativeSelect>
              </FormControl>
              日
            </div>
            <div className="switchBalanceArea">
              <SwitchBalanceButton />
            </div>
            <div className="deleteArea">
              <IconButton aria-label="delete">
                <DeleteIcon />
              </IconButton>
            </div>
          </div>
        </div>

        <div className="addArea">
          <IconButton>
            <AddCircleIcon />
          </IconButton>
        </div>

        <div className="fixedSettingsButtons">
          <Button variant="contained" color="inherit" sx={"color:#757575"}>
            キャンセル
          </Button>
          <Button variant="contained">登録</Button>
        </div>
      </div>
    </div>
  );
};
export default Settings;
