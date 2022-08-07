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
      <div className="container-box">
        <p>ユーザー設定変更</p>
        <hr />
        <div className="email-box">
          <span>メールアドレス</span>
          <TextField id="standard-basic" variant="standard" fullWidth={true} />
        </div>

        <div className="user-settings-buttons">
          <Button variant="contained" color="inherit" sx={"color:#757575"}>
            キャンセル
          </Button>
          <Button variant="contained">登録</Button>
        </div>
      </div>

      {/* パスワード変更 */}
      <div className="container-box">
        <p>パスワード変更</p>
        <hr />
        <div className="password-box">
          <span>現在のパスワード</span>
          <TextField id="standard-basic" variant="standard" fullWidth={true} />
        </div>
        <div className="password-box">
          <span>変更後のパスワード</span>
          <TextField id="standard-basic" variant="standard" fullWidth={true} />
        </div>
        <div className="password-box">
          <span>再入力</span>
          <TextField id="standard-basic" variant="standard" fullWidth={true} />
        </div>

        <div className="password-settings-buttons">
          <Button variant="contained" color="inherit" sx={"color:#757575"}>
            キャンセル
          </Button>
          <Button variant="contained">登録</Button>
        </div>
      </div>

      {/* 固定費の編集 */}
      <div className="container-box">
        <p>固定費の編集</p>
        <hr />
        <div className="fixed-list-area">
          <div className="fixed-data">
            <div className="category-data">
              <Button variant="text" sx={"color:#424242"}>
                住宅/家賃
              </Button>
            </div>
            <div className="transaction-name-data">
              <span>取引名</span>
              <TextField id="standard-basic" variant="standard" />
            </div>
            <div className="fixed-amount-data">
              <span>金額</span>
              <TextField id="standard-basic" variant="standard" />
            </div>
            <div className="transfer-date">
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
            <div className="switch-balance-area">
              <SwitchBalanceButton />
            </div>
            <div className="delete-area">
              <IconButton aria-label="delete">
                <DeleteIcon />
              </IconButton>
            </div>
          </div>
        </div>

        <div className="add-area">
          <IconButton>
            <AddCircleIcon />
          </IconButton>
        </div>

        <div className="fixed-settings-buttons">
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
