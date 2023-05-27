# MoneyHooks

React を用いた家計簿アプリケーションです。

## 概要

毎月自由に使っても良いお金と、家賃や食費などの固定費を分けて一覧で見れたら便利だなと思い開発に入りました

## 目的

「家計簿機能」、「したつもり貯金機能」を利用できる。家計の貸借対照表や損益計算書などの財務分析が行える。

## 機能について

### ホーム画面

カテゴリ毎にまとまったデータを確認できる。また、カテゴリ毎に合計金額をグラフに表示し、支出割合を確認する画面

![home](https://user-images.githubusercontent.com/93502995/235864090-1a71d98a-0594-4215-9354-10a3c7633285.png)

### タイムライン画面

日付順にソートされた収支データを確認できる。また、月別の合計支出を棒グラフで表示し、毎月の支出総額の比較を確認できる画面

![timeline](https://user-images.githubusercontent.com/93502995/235864127-01d49506-ed8c-4d4b-aba6-e4c1aae52ca8.png)

![timeline(2)](https://user-images.githubusercontent.com/93502995/235864779-ab55aacf-5108-42d9-bbdc-6fa3313c44b9.png)

### 入力画面

収支データの入力を行う。「固定費として計算する」ボタン押下で固定支出/変動支出の振り分けを行う

![input](https://user-images.githubusercontent.com/93502995/235864171-49d26d5c-f2b9-4c87-b1f6-86c46c2161ba.png)

一括入力

![inputlist](https://user-images.githubusercontent.com/93502995/235864181-f6e8ecaf-2ae9-400c-961b-a4bcc1e0a151.png)

### 変動費分析画面

固定費として計算しないデータを対象に集計を行う。カテゴリ・サブカテゴリ毎に集計を行い、データの確認を行う画面

![variablemonthly](https://user-images.githubusercontent.com/93502995/235864211-886d778e-c462-4a1e-bfee-fbf684fc0e6b.png)

### 固定費画面

収入から固定費として計算するデータを差し引き、自由に使える金額の確認が可能

![fixedmonthly](https://user-images.githubusercontent.com/93502995/235864232-2a0f89b7-d899-4701-adaf-fdf0cda1cfdd.png)

### 貯金一覧画面

「一駅歩いた」「無駄遣いを我慢した」「安いプランを探して節約できた」など、浮いたお金を集計できる機能

![savinglist](https://user-images.githubusercontent.com/93502995/235864278-1881f82b-ecb8-408b-a5f1-c9dcde8c4cf7.png)

### 貯金総額画面

累計の貯金額が確認できる画面。また、貯金目標の登録・貯金目標への積立も可能

![totalsaving](https://user-images.githubusercontent.com/93502995/235864318-b46c8c3c-2cb6-4707-9956-78f3602541e6.png)

![totalsaving(2)](https://user-images.githubusercontent.com/93502995/235864323-3378011f-beb7-4c75-809e-dc148038671e.png)
