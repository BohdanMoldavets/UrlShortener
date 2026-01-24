import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { api } from '../api';

export const RedirectPage = () => {
    const { shortId } = useParams();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [longUrl, setLongUrl] = useState('');

    useEffect(() => {
        const fetchAndRedirect = async () => {
            try {
                const response = await api.get(`/v1/urls/${shortId}`);
                console.log(response.data);
                let url = response.data?.long_url;

                if (!url) {
                    setError("Link not found.");
                    return;
                }

                setLongUrl(url);

                if (!/^https?:\/\//i.test(url)) {
                    url = "http://" + url;
                }

                console.log('Redirecting to:', url);
                window.location.href = url;
            } catch (err) {
                console.error("Error getting link:", err);
                setError("Error getting link.");
            } finally {
                setLoading(false);
            }
        };

        fetchAndRedirect();
    }, [shortId]);


    if (loading) return <p>Redirecting...</p>;
    if (error) return <p style={{ color: 'red' }}>{error}</p>;
};
