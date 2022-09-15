import React from "react";
import { Link } from "react-router-dom";

const NotFound = () => {
  return (
    <div>
      <ul>
        <li>
          <Link to={"/"}>ホーム</Link>
        </li>
        <li>
          <Link to={"/timeline"}>タイムライン</Link>
        </li>
        <li>
          <Link to={"/monthlyVariable"}>変動費</Link>
        </li>
        <li>
          <Link to={"/monthlyFixed"}>固定費</Link>
        </li>
        <li>
          <Link to={"/savingList"}>貯金一覧</Link>
        </li>
        <li>
          <Link to={"/totalSaving"}>貯金総額</Link>
        </li>
        <li>
          <Link to={"/settingsPage"}>設定</Link>
        </li>
      </ul>
    </div>
  );
};

export default NotFound;
