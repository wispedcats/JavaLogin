# Frontend

Next.js frontend for Spring Boot Login, using TypeScript and Tailwind CSS.
The home page is blank and preserves the starter background in light and dark mode.

## Development

Run from the `frontend` directory:

```bash
npm install
npm run dev
```

Open http://localhost:3000. Edit `pages/index.tsx` to add page content,
`pages/_app.tsx` for the shared layout, and `styles/globals.css` for global styles.

## Checks and production

```bash
npm run lint
npm run build
npm start
```

## Adding routes

This project uses the Next.js Pages Router. Add a `.tsx` file directly in
`pages/` with a default-exported React component:

- `pages/index.tsx` → `/`
- `pages/register.tsx` → `/register`
- `pages/login.tsx` → `/login` (example for a future route)

`pages/_app.tsx` provides the shared background, fonts, and global styles.
`pages/_document.tsx` defines the HTML document.
