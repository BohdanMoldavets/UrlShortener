import { useState } from 'react';
import { api } from '../api';

const LinkShortener = ({ children, shortUrl, setShortUrl }) => {
    const [url, setUrl] = useState('');
    const [copied, setCopied] = useState(false);
    const [isResultVisible, setIsResultVisible] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/v1/urls', { long_url: url });
            const shortPath = response.data.short_url.split('/').pop();
            setShortUrl(`http://localhost:5173/${shortPath}`);
            setIsResultVisible(true);
        } catch (error) {
            console.error('Error while shortening link:', error);
        }
    };

    const handleBack = () => {
        setIsResultVisible(false);
        setTimeout(() => {
            setShortUrl('');
            setUrl('');
        }, 500);
    };

    const handleCopy = (e) => {
        e.preventDefault();
        navigator.clipboard.writeText(shortUrl)
            .then(() => {
                setCopied(true);
                setTimeout(() => setCopied(false), 2000);
            })
            .catch(err => {
                console.error('Failed to copy: ', err);
            });
    };

    return children({
        url,
        setUrl,
        shortUrl,
        copied,
        isResultVisible,
        handleSubmit,
        handleBack,
        handleCopy,
    });
};

export default LinkShortener;
