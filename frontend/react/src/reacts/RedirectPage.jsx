import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

export const RedirectPage = () => {
    const { shortId } = useParams();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchAndRedirect = async () => {
            try {
                const response = await axios.get(`/api/v1/urls/${shortId}`);
                let longUrl = response.data?.long_url;

                if (!longUrl) {
                    setError("Не удалось найти длинную ссылку.");
                    return;
                }

                // Добавляем http, если нужно
                if (!/^https?:\/\//i.test(longUrl)) {
                    longUrl = "http://" + longUrl;
                }

                window.location.href = longUrl;

            } catch (err) {
                setError("Ошибка при получении ссылки.");
                console.error("Ошибка при получении ссылки:", err);
            } finally {
                setLoading(false);
            }
        };

        fetchAndRedirect();
    }, [shortId]);

    if (loading) {
        return <p>Перенаправление...</p>;
    }

    return <p>{error || 'Перенаправление завершено'}</p>;
};
