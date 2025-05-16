import { useEffect, useState } from 'react';

export const ShortLinkInfo = ({ shortUrl, showInfo }) => {
    const [info, setInfo] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchInfo = async () => {
            if (!shortUrl || !showInfo) return;

            const shortCode = shortUrl.split('/').pop();
            console.log('Short code:', shortCode);


            try {
                setLoading(true);
                const response = await fetch(`/api/v1/urls/info/${shortCode}`);
                console.log('Response status:', response.status);
                if (!response.ok) throw new Error("Not Found");
                const data = await response.json();
                setInfo(data);
                setError(null);
            } catch (err) {
                console.error('Fetch info error:', err);
                setInfo(null);
                setError("Info not found");
            } finally {
                setLoading(false);
            }
        };

        fetchInfo();
    }, [shortUrl, showInfo]);


    if (!showInfo) return null;
    if (loading) return <div className="info-loading">Loading info...</div>;
    if (error) return <div className="info-error">{error}</div>;
    if (!info) return null;

    return (
        <div className="info">
            <h2>Link Information</h2>
            <ul>
                <li><strong>Long URL:</strong> {info.long_url || 'N/A'}</li>
                <li><strong>Short URL:</strong> {info.short_url || 'N/A'}</li>
                <li><strong>Expires:</strong> {info.expires_date ? new Date(info.expires_date).toLocaleString() : 'N/A'}</li>
                <li><strong>Status:</strong> {info.link_status || 'N/A'}</li>
                <li><strong>Total Clicks:</strong> {info.total_clicks ?? 'N/A'}</li>
            </ul>
        </div>
    );
};
