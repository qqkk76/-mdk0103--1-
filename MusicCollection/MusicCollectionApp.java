import java.util.*;

// Интерфейс для воспроизводимых объектов
interface Playable {
    int getDuration();
    void play();
}

// Абстрактный класс жанра
abstract class MusicalGenre {
    private String name;

    public MusicalGenre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre: " + name;
    }
}

// Конкретные жанры (наследование)
class Rock extends MusicalGenre {
    public Rock() {
        super("Rock");
    }
}

class Pop extends MusicalGenre {
    public Pop() {
        super("Pop");
    }
}

class Jazz extends MusicalGenre {
    public Jazz() {
        super("Jazz");
    }
}

// Класс композиции
class MusicalComposition implements Playable {
    private String title;
    private String artist;
    private int duration; // в секундах
    private MusicalGenre genre;

    // Конструктор
    public MusicalComposition(String title, String artist, int duration, MusicalGenre genre) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.genre = genre;
    }

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration > 0) {
            this.duration = duration;
        }
    }

    public MusicalGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicalGenre genre) {
        this.genre = genre;
    }

    // Реализация интерфейса
    @Override
    public void play() {
        System.out.println("Playing: " + title + " by " + artist + " [" + genre.getName() + "]");
    }

    // Переопределение toString
    @Override
    public String toString() {
        return String.format("Title: %s, Artist: %s, Duration: %d sec, Genre: %s",
                title, artist, duration, genre.getName());
    }

    // Переопределение equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MusicalComposition that = (MusicalComposition) obj;
        return duration == that.duration &&
                Objects.equals(title, that.title) &&
                Objects.equals(artist, that.artist) &&
                Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, duration, genre);
    }
}

// Класс диска (сборки)
class MusicDisk {
    List<MusicalComposition> tracks;
    private String diskName;

    public MusicDisk(String diskName) {
        this.diskName = diskName;
        this.tracks = new ArrayList<>();
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    // Добавление трека
    public void addTrack(MusicalComposition track) {
        tracks.add(track);
    }

    // Подсчёт общей продолжительности
    public int getTotalDuration() {
        return tracks.stream().mapToInt(Playable::getDuration).sum();
    }

    // Перестановка по жанру
    public void sortByGenre() {
        tracks.sort(Comparator.comparing(track -> track.getGenre().getName()));
    }

    // Поиск треков в диапазоне длительности
    public List<MusicalComposition> findTracksByDuration(int minDuration, int maxDuration) {
        return tracks.stream()
                .filter(track -> track.getDuration() >= minDuration && track.getDuration() <= maxDuration)
                .toList();
    }

    // Вывод всех треков
    public void printAllTracks() {
        System.out.println("\n=== " + diskName + " Tracklist ===");
        tracks.forEach(System.out::println);
        System.out.println("Total duration: " + getTotalDuration() + " sec");
    }

    @Override
    public String toString() {
        return "MusicDisk{" +
                "name='" + diskName + '\'' +
                ", trackCount=" + tracks.size() +
                '}';
    }
}

// Основное приложение
public class MusicCollectionApp {
    public static void main(String[] args) {
        // Создаём диск
        MusicDisk disk = new MusicDisk("My Favorite Tracks");

        // Добавляем треки
        disk.addTrack(new MusicalComposition("Bohemian Rhapsody", "Queen", 355, new Rock()));
        disk.addTrack(new MusicalComposition("Billie Jean", "Michael Jackson", 294, new Pop()));
        disk.addTrack(new MusicalComposition("Take Five", "Dave Brubeck", 198, new Jazz()));
        disk.addTrack(new MusicalComposition("Stairway to Heaven", "Led Zeppelin", 482, new Rock()));
        disk.addTrack(new MusicalComposition("Imagine", "John Lennon", 183, new Pop()));

        // Выводим исходный список
        disk.printAllTracks();

        // Сортируем по жанру
        System.out.println("\nSorting by genre...");
        disk.sortByGenre();
        disk.printAllTracks();

        // Ищем треки от 180 до 300 секунд
        System.out.println("\nTracks with duration 180-300 sec:");
        List<MusicalComposition> filtered = disk.findTracksByDuration(180, 300);
        filtered.forEach(System.out::println);

        // Проигрываем первый трек
        System.out.println("\nNow playing:");
        if (!disk.tracks.isEmpty()) {
            disk.tracks.get(0).play();
        }
    }
}
