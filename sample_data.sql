-- テーマカラー
INSERT INTO
    theme_color(theme_color_code)
VALUES
    ('#76d5ff'),
    ('#607d8b'),
    ('#212121'),
    ('#43a047'),
    ('#00acc1'),
    ('#e53935'),
    ('#8e24aa');

INSERT INTO
    theme_color(theme_color_gradient_code)
VALUES
    ('#355C7D, #C06C84'),
    ('#11998e, #38ef7d'),
    ('#108dc7, #ef8e38'),
    ('#FC5C7D, #6A82FB'),
    ('#74ebd5, #ACB6E5'),
    ('#36D1DC, #5B86E5');

-- ユーザー
INSERT INTO
    user(user_id, email, PASSWORD)
VALUES
    (
        '4f4da417-7693-4fa1-b153-a3511ed1a57a',
        'MasterUser',
        'password'
    ),
    (
        'a77a6e94-6aa2-47ea-87dd-129f580fb669',
        'sample@sample.com',
        'password'
    );

-- カテゴリ
INSERT INTO
    category(category_id, category_name)
VALUES
    (1, '食費'),
    (2, '外食'),
    (3, 'コンビニ'),
    (4, '日用品'),
    (5, 'ショッピング'),
    (6, 'ファッション'),
    (7, 'WEBサービス'),
    (8, 'エンタメ'),
    (9, '趣味'),
    (10, '旅行・レジャー'),
    (11, '交際費'),
    (12, 'ギフト'),
    (13, '交通費'),
    (14, '美容・コスメ'),
    (15, '医療・健康'),
    (16, '車'),
    (17, '教育'),
    (18, '子供'),
    (19, '手数料'),
    (20, '水道光熱費'),
    (21, '通信費'),
    (22, '住宅'),
    (23, '税金'),
    (24, '保険'),
    (25, '返済'),
    (26, 'ビジネス'),
    (27, '給与'),
    (28, 'その他収入');

-- サブカテゴリ
INSERT INTO
    sub_category(user_no, category_id, sub_category_name)
VALUES
    (1, 1, 'なし'),
    (1, 2, 'なし'),
    (1, 2, '外食'),
    (1, 2, 'カフェ'),
    (1, 2, 'お昼'),
    (1, 2, 'レストラン'),
    (1, 3, 'なし'),
    (1, 4, 'なし'),
    (1, 5, 'なし'),
    (1, 5, 'ショッピング'),
    (1, 5, 'スポーツ用品'),
    (1, 5, 'ペット'),
    (1, 5, '電化製品'),
    (1, 6, 'なし'),
    (1, 7, 'なし'),
    (1, 8, 'なし'),
    (1, 8, 'エンタメ'),
    (1, 8, '本・雑誌'),
    (1, 8, 'ゲーム'),
    (1, 8, '音楽'),
    (1, 8, '映画'),
    (1, 8, 'テレビ'),
    (1, 9, 'なし'),
    (1, 10, 'なし'),
    (1, 10, '旅行・レジャー'),
    (1, 10, '博物館・劇場'),
    (1, 10, '遊園地'),
    (1, 10, 'ホテル'),
    (1, 10, '温泉'),
    (1, 11, 'なし'),
    (1, 12, 'なし'),
    (1, 13, 'なし'),
    (1, 13, '交際費'),
    (1, 13, 'ゲームセンター'),
    (1, 13, 'カラオケ'),
    (1, 13, '居酒屋・バー'),
    (1, 14, 'なし'),
    (1, 14, '美容・コスメ'),
    (1, 14, '美容'),
    (1, 14, 'コスメ'),
    (1, 15, 'なし'),
    (1, 15, '医療・健康'),
    (1, 15, '眼科'),
    (1, 15, '薬'),
    (1, 15, '病院'),
    (1, 15, 'ジム・フィットネス'),
    (1, 16, 'なし'),
    (1, 16, '車'),
    (1, 16, 'ガソリン'),
    (1, 16, '駐車場'),
    (1, 16, '自動車保険'),
    (1, 16, '有料道路'),
    (1, 16, '自動車税'),
    (1, 16, '維持費'),
    (1, 17, 'なし'),
    (1, 17, '教育'),
    (1, 17, '学費'),
    (1, 17, '学生ローン返済'),
    (1, 17, '教科書等'),
    (1, 18, 'なし'),
    (1, 18, '子供'),
    (1, 18, '小児科'),
    (1, 18, '保育園'),
    (1, 19, 'なし'),
    (1, 19, '手数料'),
    (1, 19, '利息'),
    (1, 19, '振込手数料'),
    (1, 19, '銀行手数料'),
    (1, 19, 'ATM手数料'),
    (1, 20, 'なし'),
    (1, 20, '水道光熱費'),
    (1, 20, '電気'),
    (1, 20, '水道'),
    (1, 20, 'ガス'),
    (1, 21, 'なし'),
    (1, 21, '通信費'),
    (1, 21, '携帯電話'),
    (1, 21, '固定電話'),
    (1, 21, 'インターネット'),
    (1, 22, 'なし'),
    (1, 22, '住宅'),
    (1, 22, '家賃'),
    (1, 22, '住宅設備'),
    (1, 22, '保険'),
    (1, 23, 'なし'),
    (1, 23, '税金'),
    (1, 23, '住民税'),
    (1, 24, 'なし'),
    (1, 24, '保険'),
    (1, 24, 'その他の保険'),
    (1, 24, '生命保険'),
    (1, 25, 'なし'),
    (1, 25, 'カード引落し'),
    (1, 25, '返済'),
    (1, 26, 'なし'),
    (1, 26, 'ビジネス'),
    (1, 26, 'オフィス用品'),
    (1, 26, '法務会計'),
    (1, 26, '送料'),
    (1, 26, 'オフィス設備'),
    (1, 26, '印刷'),
    (1, 27, 'なし'),
    (1, 27, 'ボーナス'),
    (1, 28, 'なし'),
    (1, 28, '家賃所得'),
    (1, 28, '利子所得'),
    (1, 28, 'お小遣い');

INSERT INTO
    sub_category(user_no, category_id, sub_category_name)
VALUES
    (2, 2, 'ラーメン巡り'),
    (2, 2, '寿司巡り'),
    (2, 2, 'カフェ巡り'),
    (2, 7, 'Amazon Prime'),
    (2, 7, 'Youtube Premium'),
    (2, 9, '自転車用品'),
    (2, 22, 'DIY');

-- 非表示サブカテゴリ
INSERT INTO
    hidden_sub_category(user_no, sub_category_id)
VALUES
    (2, 102),
    (2, 103),
    (2, 4);

-- 取引
INSERT INTO
    transaction(
        user_no,
        transaction_name,
        transaction_amount,
        transaction_date,
        category_id,
        sub_category_id,
        fixed_flg
    )
VALUES
    (2, 'スーパーアルプス', -3000, '2022-07-01', 1, 1, false),
    (2, 'スーパーアルプス', -1800, '2022-07-08', 1, 1, false),
    (2, 'カフェくるくま', -1200, '2022-07-13', 2, 104, false),
    (2, 'スーパーアルプス', -3000, '2022-07-15', 1, 1, false),
    (2, 'ルームサンダル', -3800, '2022-07-17', 5, 11, false),
    (2, 'スーパーアルプス', -5200, '2022-07-22', 1, 1, false),
    (2, 'タバコ', -550, '2022-07-25', 3, 7, false),
    (2, '家賃', -78550, '2022-07-25', 22, 82, TRUE),
    (2, 'Youtube', -1000, '2022-07-28', 7, 105, TRUE),
    (2, 'Amazon', -500, '2022-07-28', 7, 106, TRUE);

-- 貯金目標
INSERT INTO
    saving_target(
        user_no,
        saving_target_name,
        target_amount,
        delete_flg
    )
VALUES
    (2, 'M2 MacBook Air', 160000, false),
    (2, '沖縄旅行', 1000000, false);

-- 貯金
INSERT INTO
    saving(
        user_no,
        saving_name,
        saving_amount,
        saving_date,
        saving_target_id
    )
VALUES
    (2, 'タバコ', 450, '2022-07-01', 1),
    (2, 'ジャージ', 4500, '2022-07-02', 1),
    (2, 'モニター', 23000, '2022-07-05', 1),
    (2, 'フライパン', 1800, '2022-07-06', 1),
    (2, '観葉植物', 1500, '2022-07-08', 1),
    (2, 'タバコ', 10000, '2022-07-13', 1),
    (2, '交通費', 350, '2022-07-15', 1),
    (2, 'キーボード', 8000, '2022-07-15', 2),
    (2, 'PlayStation5', 50000, '2022-07-16', 2),
    (2, 'ディフューザー', 1300, '2022-07-19', 2),
    (2, 'スピーカー', 8000, '2022-07-20', 2),
    (2, 'サングラス', 9000, '2022-07-22', 2),
    (2, '交通費', 300, '2022-07-24', 2),
    (2, '映画', 1800, '2022-07-25', 2),
    (2, 'お酒', 200, '2022-07-28', 1);

-- 月次取引
INSERT INTO
    monthly_transaction(
        user_no,
        monthly_transaction_name,
        monthly_transaction_amount,
        monthly_transaction_date,
        category_id,
        sub_category_id,
        include_flg
    )
VALUES
    (2, '家賃', -78550, '31', 22, 82, TRUE),
    (2, '見放題chライト', -550, '27', 7, 15, TRUE),
    (2, 'Youtube Premium', -1150, '25', 7, 15, TRUE),
    (2, 'DisneyPlus', -980, '25', 7, 15, TRUE),
    (2, '給与', 200000, '25', 27, 102, TRUE),
    (2, 'Amazon', -550, '25', 7, 15, false);