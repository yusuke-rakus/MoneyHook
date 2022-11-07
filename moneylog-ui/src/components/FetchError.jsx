/** 設定ページの通信エラー */
export const SettingsFetchErrorOnlyBanner = (setBanner) => {
  setBanner({
    banner: true,
    bannerMessage: "不明なエラーが発生しました",
    bannerType: "error",
  });
};

export const SettingsFetchError = (setLoading, setBanner) => {
  setLoading(false);
  setBanner({
    banner: true,
    bannerMessage: "不明なエラーが発生しました",
    bannerType: "error",
  });
};

/** 初期値取得エラー */
export const LoadFetchError = (setLoading, setBanner) => {
  setLoading(false);
  setBanner({
    banner: true,
    bannerMessage: "不明なエラーが発生しました",
    bannerType: "error",
  });
};

export const LoadFetchErrorWithSeparateBanner = (
  setLoading,
  setBanner,
  setBannerMessage,
  setBannerType
) => {
  setLoading(false);
  setBanner(true);
  setBannerMessage("不明なエラーが発生しました");
  setBannerType("error");
};

/** データ登録エラー */
export const PostError = (setLoading, setBanner, closeModalWindow) => {
  setLoading(false);
  setBanner({
    banner: true,
    bannerMessage: "不明なエラーが発生しました",
    bannerType: "error",
  });
  closeModalWindow();
};
export const PostErrorWithSeparateBanner = (
  setLoading,
  setBanner,
  setBannerMessage,
  setBannerType,
  closeModalWindow
) => {
  setLoading(false);
  setBanner(true);
  setBannerMessage("不明なエラーが発生しました");
  setBannerType("error");
  closeModalWindow();
};
