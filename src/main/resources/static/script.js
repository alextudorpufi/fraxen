document.addEventListener("DOMContentLoaded", () => {
    // Fade-in animations
    const faders = document.querySelectorAll('.fade-in');
    const appearOptions = { threshold: 0.2 };
    const appearOnScroll = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (!entry.isIntersecting) return;
            entry.target.classList.add('visible');
            observer.unobserve(entry.target);
        });
    }, appearOptions);

    faders.forEach(fader => appearOnScroll.observe(fader));

    // Simple tracking call
    fetch("/track")
        .catch(err => console.log("Tracker failed:", err))
        .then(() => console.log("Tracker success"));

    // Language toggle
    const langBtn = document.getElementById('language-toggle');
    const urlParams = new URLSearchParams(window.location.search);
    const currentLang = urlParams.get('lang') || 'en';
    console.log(currentLang);
    langBtn.textContent = currentLang.toUpperCase();
    langBtn.addEventListener('click', () => {
        let newLang = langBtn.textContent.toUpperCase() === 'EN' ? 'ro' : 'en';
        langBtn.textContent = newLang.toUpperCase();
        const url = new URL(window.location.href);
        url.searchParams.set('lang', newLang);
        window.location.href = url.toString();
    });
    // Initialize notification (Add this line)
    initNotification();

    // Initialize header logic (menu, theme, modal)
    initHeader();
});

// ---------------- Header Init (Menu, Theme, Contact Modal) ---------------- //

function initHeader() {

    initThemeToggle();

    const menuBtn = document.querySelector(".menu-toggle");
    const nav = document.querySelector("header nav");

    if (menuBtn && nav) {

        // Toggle menu on button click
        menuBtn.addEventListener("click", () => {
            nav.classList.toggle("active");
        });

        // Close menu when clicking outside
        document.addEventListener("click", (e) => {
            if (nav.classList.contains("active") &&
                !nav.contains(e.target) &&
                !menuBtn.contains(e.target)) {
                nav.classList.remove("active");
            }
        });
    }
    console.log("test");

    // Contact modal logic
    const requestExecutiveButton = document.getElementById('requestExecutiveButton');
    const contactButton = document.getElementById('contactButton');
    const mailModal = document.getElementById('mailModal');
    const yesButton = document.getElementById('yesButton');
    const noButton = document.getElementById('noButton');
    const emailButton = document.getElementById('emailButton');

    let currentTrigger = null;

    if (!contactButton || !mailModal) return;

    // Open modal
    contactButton.addEventListener('click', () => {
        currentTrigger = 'contact';
        mailModal.style.display = 'flex';
    });

    requestExecutiveButton?.addEventListener('click', () => {
        currentTrigger = 'requestExecutive';
        console.log("algce");
        mailModal.style.display = 'flex';
    });

    emailButton?.addEventListener('click', (e) => {
        e.preventDefault();
        currentTrigger = 'contact';
        mailModal.style.display = 'flex';
    });

    // Modal buttons
    noButton?.addEventListener('click', () => {
        currentTrigger = null;
        mailModal.style.display = 'none';
    });

    yesButton?.addEventListener('click', () => {
        let subject, body;

        if (currentTrigger === 'requestExecutive') {
            subject = encodeURIComponent("Sent via Fraxen Website - Executive Request");
            body = encodeURIComponent("Hello Fraxen team,\n\nI would like to request an executive consultation.\n\nMy phone number:\n");
        } else {
            subject = encodeURIComponent("Sent via Fraxen Website - Contact");
            body = encodeURIComponent("Hello Fraxen team,\n\nI would like to get in touch with you.\n\nMy phone number:\n");
        }

        window.location.href = `mailto:admin@fraxen.eu?subject=${subject}&body=${body}`;
        mailModal.style.display = 'none';
    });

    // Close modal when clicking outside
    window.addEventListener('click', (e) => {
        if (e.target === mailModal) mailModal.style.display = 'none';
    });

}

// For christmas
function initNotification() {
    const notification = document.getElementById('christmasModal');
    const body = document.body;

    // 1. Check if the user has already seen this during this season
    const hasSeenNotification = sessionStorage.getItem('christmas_notified');

    if (notification && !hasSeenNotification) {
        // Lock scroll and show modal
        body.style.overflow = 'hidden';
        notification.style.display = 'flex';

        setTimeout(() => {
            notification.classList.add('notification-active');
        }, 10);

        // 2. Set the flag in localStorage so it won't show again
        sessionStorage.setItem('christmas_notified', 'true');

        setTimeout(() => {
            notification.classList.add('notification-fade');

            setTimeout(() => {
                notification.style.display = 'none';
                notification.classList.remove('notification-active', 'notification-fade');
                body.style.overflow = '';
            }, 800);
        }, 4000);
    }
}


// ---------------- Light/Dark Mode Toggle ---------------- //

function initThemeToggle() {
    const toggleBtn = document.getElementById("theme-toggle");
    const body = document.body;

    if (!toggleBtn) return;

    if (localStorage.getItem("theme") === "dark") {
        body.classList.add("dark-mode");
    }

    toggleBtn.addEventListener("click", () => {
        body.classList.toggle("dark-mode");
        const isDark = body.classList.contains("dark-mode");
        localStorage.setItem("theme", isDark ? "dark" : "light");
    });
}
