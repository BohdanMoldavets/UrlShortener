import { useEffect, useState } from "react";
import { api } from "../../api";

export const useShortLinkInfo = (shortUrl, showInfo) => {
  const [info, setInfo] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchInfo = async () => {
      if (!shortUrl || !showInfo) return;

      const shortCode = shortUrl.split("/").pop();
      try {
        setLoading(true);
        const response = await api.get(`/v1/urls/info/${shortCode}`);
        setInfo(response.data);
        setError(null);
      } catch (err) {
        setInfo(null);
        setError("Info not found");
      } finally {
        setLoading(false);
      }
    };

    fetchInfo();
  }, [shortUrl, showInfo]);

  return { info, error, loading };
};
