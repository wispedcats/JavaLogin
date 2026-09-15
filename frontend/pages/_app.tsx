import type { AppProps } from "next/app";
import Head from "next/head";
import { Geist, Geist_Mono } from "next/font/google";
import "../styles/globals.css";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <Head>
        <title>Spring Boot Login</title>
      </Head>
      <div
        className={`${geistSans.variable} ${geistMono.variable} flex min-h-screen justify-center bg-zinc-50 font-sans antialiased dark:bg-black`}
      >
        <main className="w-full max-w-3xl flex-1 bg-white dark:bg-black">
          <Component {...pageProps} />
        </main>
      </div>
    </>
  );
}
