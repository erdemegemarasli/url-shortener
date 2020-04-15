package bilshort.link.services;

import bilshort.link.models.Link;
import java.util.List;

public interface LinkService {
    Link createShortLink(Link link);
    List<Link> getAllLinks();
}
