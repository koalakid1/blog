// rollup.config.js

import dotenv from 'dotenv';
import replace from '@rollup/plugin-replace';

dotenv.config({
    override: true,
    path: ".env"
})

const production = !process.env.ROLLUP_WATCH;

export default {
    plugins: [
        replace({
            // stringify the object
            __myapp: JSON.stringify({
                env: {
                    isProd: production,
                    API_URL: process.env.API_URL
                }
            }),
        }),
    ],
};