import { useState } from 'react';
import axios from 'axios';

const LinkShortener = () => {
    const [url, setUrl] = useState('');
    const [shortUrl, setShortUrl] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/v1/urls', {
                long_url: url
            });

            setShortUrl(response.data.short_url);
        } catch (error) {
            console.error('Ошибка при сокращении ссылки:', error);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <input
                    type="url"
                    value={url}
                    onChange={(e) => setUrl(e.target.value)}
                    placeholder="Вставьте ссылку"
                    required
                />
                <button type="submit">Сократить</button>
            </form>

            {shortUrl && (
                <div>
                    <p>Сокращенная ссылка:</p>
                    <a href={shortUrl} target="_blank" rel="noopener noreferrer">{shortUrl}</a>
                </div>
            )}
        </div>
    );
};

export default LinkShortener;

