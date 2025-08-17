document.addEventListener("DOMContentLoaded", () => {
    loadFragment("header.html", "#header");
    loadFragment("footer.html", "#footer");
});

const faders = document.querySelectorAll('.fade-in');

const appearOptions = {
    threshold: 0.2
};

const appearOnScroll = new IntersectionObserver(function(entries, observer) {
    entries.forEach(entry => {
        if (!entry.isIntersecting) return;
        entry.target.classList.add('visible');
        observer.unobserve(entry.target);
    });
}, appearOptions);

faders.forEach(fader => {
    appearOnScroll.observe(fader);
});

function loadFragment(file, target) {
    fetch(file)
        .then(response => response.text())
        .then(data => {
            document.querySelector(target).innerHTML = data;
        });
}
